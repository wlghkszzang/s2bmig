# Migration Lessons Learned (마이그레이션 학습 기록)

이 문서는 `api-bo` (Legacy) 시스템에서 `migproject` (Spring Boot 3 + Hexagonal Architecture)로 기능을 이관하면서 겪은 문제점과 해결책을 기록합니다.

## 1. MyBatis XML 쿼리 마이그레이션 이슈

### 1-1. 계층 조회 및 상속 로직 (`WITH RECURSIVE`)
*   **문제점:** 레거시 쿼리에는 계층 구조를 따라 상위의 속성(예: 마진율)을 찾는 Recursive CTE 로직이 포함되어 있으나, 이관 시 단순 조회로 변경되어 비즈니스 로직이 깨질 수 있음.
*   **해결책:** `WITH RECURSIVE` 구문을 보존하거나, 복잡한 상속 로직의 경우 Service 레이어에서 처리하도록 검토. 하지만 쿼리 성능을 위해 가급적 레거시의 효율적인 쿼리 구조를 유지함.

### 1-2. 특수 관리자용 조건 누락
*   **문제점:** 단순 목록 조회(`get...List`) 외에 관리 환경에서 필요한 수량/상태 체크 쿼리(`get...Count` 또는 전용 관리 쿼리)가 누락되기 쉬움.
*   **해결책:** 레거시 Mapper의 전체 쿼리 목록을 대조하여 매칭되는 쿼리가 모두 이관되었는지 확인.

### 1-3. XML 구문 안전성 (CDATA)
*   **문제점:** 부등호(`>`, `<`) 등이 포함된 쿼리를 직접 복사할 경우 XML 파싱 에러 또는 오동작 발생 가능.
*   **해결책:** 명확하게 `<![CDATA[ ... ]]>`로 감싸서 처리.

### 1-4. XML 문법 (따옴표 중첩)
*   **문제점:** `<if test="...">` 속성 내에서 문자열 비교 시 따옴표가 중첩될 경우 XML 파싱 에러(SAXParseException) 발생.
*   **해결책:** 바깥쪽이 큰따옴표(`"`)라면 안쪽은 반드시 홀따옴표(`'`)를 사용하거나 그 반대로 사용.
    - 예시: `<if test='dispYn == "Y"'>` (O) / `<if test="dispYn == "Y"">` (X)

### 1-5. Interface와 XML의 1:1 매칭 (Parity)
*   **문제점:** XML의 `id` 또는 `parameterType`이 인터페이스와 불일치할 경우 빈 생성 오류 또는 `ClassNotFoundException` 발생.
*   **해결책:** 
    - XML의 `id`는 반드시 Mapper 인터페이스의 메서드 명칭과 대소문자까지 일치해야 함.
    - `parameterType`은 실제로 존재하는 클래스의 풀 패키지 경로를 기재해야 함.

## 2. HTML 및 JavaScript 기능 누락 이슈

### 2-1. Grid 이벤트 및 데이터 바인딩
*   **문제점:** 레거시의 Grid(RealGrid 등)와 새 프로젝트의 Grid(AUIGrid 등) 간의 이벤트 핸들러 명칭이나 데이터 처리 방식 차이로 인해 Master-Detail 연결(예: 카테고리 클릭 시 상세 정보 조회)이 누락되는 현상.
*   **해결책:** JS 파일 내의 `onCellClick`, `onRowSelection` 등 핵심 이벤트를 전수조사하여 레거시와 동일한 데이터 호출 흐름(API Call)이 보장되는지 확인.

### 2-2. 공통 코드 및 팝업 연동
*   **문제점:** `FN_GET_CODENAME` 같은 DB 함수나 공통 코드 조회 API가 새 환경에서 지원되지 않거나 파라미터가 다를 수 있음.
*   **해결책:** 공통 코드 서비스(`/common/code/getStStdCd`) 호출 시 필요한 모든 코드 그룹이 정확히 포함되어 있는지 확인.

## 3. 계층 간 데이터 전달 규칙 (The "Transfer" Rule)

### 3-1. MapStruct 및 Transfer 레이어 누락
*   **문제점:** `OptionMgmt` 모듈에서 정립된 **`interfaces.controller.transfer`** 패턴(`MapStruct` 사용)이 `StandardCategory` 모듈 마이그레이션 시 누락됨. 엔티티를 직접 컨트롤러에 노출하거나 인터페이스 DTO를 서비스 레이어에서 직접 호출하는 것은 헥사고날 아키텍처 규칙 위반임.
*   **해결책:**
    1.  `application.queryservice.query` 및 `application.commandservice.command` 패키지를 통해 전용 데이터 캡슐 객체 정의.
    2.  `interfaces.controller.transfer` 레이어에서 `MapStruct`를 사용하여 `DTO <-> Command/Query` 변환 로직 강제화.
    3.  서비스 레이어는 오직 도메인 지향적인 `Command/Query` 객체만을 바라보도록 하여 계층 간 결합도를 최소화함.

## 4. 인프라 및 환경 설정 이슈
*   **Lombok 문제:** `@Data`, `@RequiredArgsConstructor` 등 Lombok 어노테이션이 IDE에서 인식되지 않아 발생하는 빌드 에러.
*   **해결책:** `pom.xml` 의존성 확인 및 IDE Lombok 플러그인 설정 재점검.

## 5. 마이그레이션 기술적 세부 특이사항 (AS-IS → TO-BE)

### 5-1. 그리드 컴포넌트 마이그레이션 (RealGrid → AUIGrid)
*   **특이사항:** 레거시의 `RealGrid` 기반 데이터 처리 로직(변경점 체크 등)이 `migproject`의 `AUIGrid` 표준으로 변경됨.
*   **해결책:** 
    - `AUIGrid.getAddedRowItems()`, `getEditedRowItems()`, `getRemovedItems()` 등을 활용하여 변경된 데이터만 서버로 전송하는 배치(Bulky) CUD 로직을 정규화함.

### 5-2. 리소스 파일(Properties) 의존성 제거 및 한글명 직접 기입
*   **특이사항:** 신규 프로젝트의 가독성과 전파 속도를 높이기 위해, 별도의 `messages.properties` 파일을 생성하거나 참조하지 않음.
*   **해결책:** 
    - 레거시의 `properties` 키가 가리키는 **실제 한글명(Korean text)**을 JavaScript(`alert`, `confirm` 등) 및 MyBatis XML(주석 등)에 직접 기입하여 직관성을 높임.
    - 예시: `msg.success.save` → `"성공적으로 저장되었습니다."`
