# 표준 마이그레이션 아키텍처 가이드 (The Transfer Rule)

이 문서는 `migproject`의 핵심 아키텍처 설계 원칙과 계층 간 데이터 전송 규칙을 정의합니다. 모든 모듈 마이그레이션 시 이 구조를 엄격히 준수해야 합니다.

---

## 1. 계층 구조 (Layered Architecture)

프로토콜에 따른 4계층 구조를 유지하며, 각 계층은 자신만의 전용 객체를 가집니다.

| 계층 | 역할 | 주요 객체 |
| :--- | :--- | :--- |
| **Interface (Web)** | 외부 입출력 관리 (Adapter) | `ReqDto`, `RspDto<T>`, `CudDto`, `RestController`, `Transfer (MapStruct)` |
| **Application** | 비즈니스 흐름 제어 (Orchestration) | `Query`, `Command`, `QueryResponse`, `Service`, `AppTransfer (MapStruct)` |
| **Domain** | 핵심 비즈니스 로직 및 규칙 (Model) | `Entity`, `Model`, `QueryResult`, `QueryParam`, `Repository (Interface/Port)` |
| **Infrastructure** | 데이터 저장 및 외부 연동 (Adapter) | `MyBatisDao`, `RepositoryImpl`, `MyBatis XML` |

---

## 2. 데이터 변환 규칙 (The Transfer Rule)

계층 간에는 **절대로 엔티티(Entity)나 타 계층의 DTO를 직접 노출하지 않습니다.** 데이터는 반드시 전용 `Transfer`를 통해 변환되어야 합니다.

### 2.1 Interface Layer Transfer (`*DtoTransfer`)
- **위치:** `interfaces.controller.transfer` (또는 `interfaces.api.transfer`)
- **의존성 방향:** `Interface` -> `Application` (가능), **`Application` -> `Interface` (절대 불가)**
- **역할:** 웹 요청(`ReqDto`)을 애플리케이션 명령(`Query`/`Command`)으로 변환하고, 응답(`QueryResponse`)을 외부용(`RspDto`)으로 최종 매핑.

### 2.2 Application Layer Transfer (`*AppTransfer`)
- **위치:** `application.transfer`
- **의존성 방향:** `Application` -> `Domain` (가능)
- **역할:** 애플리케이션 명령(`Query`/`Command`)을 도메인 엔진용(`QueryParam`) 또는 `Domain Model`로 변환. 또한 도메인 결과(`QueryResult`/`Entity`)를 응답용(`QueryResponse`)으로 매핑.

### 2.3 Infrastructure Layer Transfer (`*DbTransfer`)
- **위치:** `infrastructure.db.transfer`
- **의존성 방향:** `Infrastructure` -> `Domain` (가능)
- **역할:** 데이터베이스 엔티티(`Entity`)를 도메인 결과 객체(`QueryResult`)로 변환하거나 그 반대의 매핑을 수행. (현재는 MyBatis direct mapping 위주이나 구조적 확보 필수)

### 2.3 Layer Dependency Constraint (의존성 제약)
| 계층 | 허용되는 참조 | 금지되는 참조 |
| :--- | :--- | :--- |
| **Interface** | `Application`, `Domain` | 없음 |
| **Application** | `Domain` | `Interface` (DTO 등을 참조하면 아키텍처 오염) |
| **Domain** | 없음 (완전 고립) | `Application`, `Interface` |

> [!CAUTION]
> **Interface 계층의 Transfer에서 Domain Entity나 Model을 직접 참조하여 변환하는 행위는 금지됩니다.** 
> 모든 데이터는 Application 계층의 `QueryResponse`나 `CommandResult` 객체로 한 번 감싸서 인터페이스로 전달되어야 하며, `DtoTransfer`는 이 객체들을 `RspDto`로 매핑하는 역할만 수행합니다.

> [!IMPORTANT]
> 모든 `Transfer`는 **MapStruct** 사용을 원칙으로 하며, 직접적인 수동 매핑(Setter 호출 등)은 지양합니다.

---

## 3. CQRS (조회와 명령의 분리)

조회(Read)와 명령(Create, Update, Delete)의 책임을 명확히 분리합니다.

### 3.1 Repository 분리
- `StandardCategoryQueryRepository`: 조회 전용 인터페이스
- `StandardCategoryCommandRepository`: 수정 전용 인터페이스

### 3.2 Service 분리
- `StandardCategoryQueryService`: 조회 로직 담당
- `StandardCategoryCommandService`: 수정 로직 담당

---

## 4. MyBatis SQL 가이드

MyBatis XML 작성 시 엔티티를 직접 `resultType`으로 사용하지 않고, 도메인 계층의 전용 객체를 사용합니다.

- **파라미터:** `QueryParam` (조사용 파라미터 캡슐화)
- **반환값:** `QueryResult` (조회 결과 캡슐화)

### 💡 주의사항 (Anti-Pattern)
- ❌ 컨트롤러에서 엔티티(`PrStdCtg`)를 직접 리턴하는 행위
- ❌ 서비스 계층 없이 컨트롤러에서 레포지토리를 직접 호출하는 행위
- ❌ MyBatis XML에서 `Map`이나 `Entity`를 남발하여 데이터 구조를 불투명하게 만드는 행위

---

## 5. 명명 규칙 (Naming Convention)

| 접미사 | 설명 | 예시 |
| :--- | :--- | :--- |
| **ReqDto** | 웹 요청 파라미터 (Query용) | `StandardCategoryReqDto` |
| **CudDto** | 웹 요청 데이터 (저장용) | `StandardCategoryCudDto` |
| **RspDto** | 웹 응답 데이터 | `StandardCategoryMgmtRspDto` |
| **Query** | 애플리케이션 계층 조회 명령 | `GetStandardCategoryQuery` |
| **Command** | 애플리케이션 계층 수정 명령 | `SaveStandardCategoryCommand` |
| **QueryResult** | 도메인 계층 조회 결과 | `StandardCategoryMgmtQueryResult` |
| **QueryParam** | 도메인 계층 조회 파라미터 | `StandardCategoryMgmtQueryParam` |

## 6. 마이그레이션 기술적 특이사항 (AS-IS → TO-BE)

레거시 시스템에서 신규 프로젝트로 전환 시 다음의 기술적 변경 사항 및 타겟 원본 소스를 반드시 준수합니다.

### 6.0 AS-IS 대상 프로젝트 기준 (중요)
- **AS-IS Front 화면:** `moon-x2bee-bo-vanilla-main` 프로젝트 기준
- **AS-IS API:** `moon-x2bee-api-bo-vanilla-main` 프로젝트 기준
- 참고: 마이그레이션 시 기존 `x2bee-bo` 폴더가 아닌 위 두 폴더의 소스를 최우선으로 참고해야 합니다.

### 6.1 프론트엔드 개발 표준 (Vanilla JS Only)
- **원칙:** 신규 `migproject`의 프론트엔드는 **Vanilla JS(순수 자바스크립트)**만을 사용합니다.
- **jQuery 사용 금지:** AS-IS의 `$` (jQuery) 문법을 절대 사용하지 않으며, 모든 라이브러리 연동 및 이벤트 처리는 표준 Web API를 기반으로 합니다.
- **데이터 통신:** jQuery의 `$.ajax` 대신 브라우저 표준인 **`fetch` API** 또는 프로젝트 공통 Vanilla JS 통신 모듈을 사용합니다.
- **DOM 조작 및 이벤트:**
  - `document.querySelector`, `document.getElementById` 사용
  - `element.addEventListener('click', ...)` 등 표준 이벤트 리스너 활용

### 6.2 그리드 컴포넌트 전환
- **AS-IS:** `RealGrid`
- **TO-BE:** `AUIGrid`
- **주요 차이점:**
  - 데이터 변경 감지: `getAddedRowItems()`, `getEditedRowItems()`, `getRemovedItems()` 등을 사용하여 변경분만 서버로 전송합니다.
  - 그리드 초기화 시 `standardCategoryMgmt.js`와 같은 모듈화된 JS 패턴을 사용합니다.
  - AUIGrid 역시 jQuery 의존성 없이 Vanilla JS 환경에서 구동되도록 설정합니다.


### 6.2 리소스 및 메시지 처리
- **규칙:** 신규 `migproject`에서는 별도의 `properties` 파일을 통한 다국어/메시지 관리를 지양합니다.
- **적용:** 기존 레거시의 `properties` 키값 대신, 해당 키가 가리키는 **실제 한글명(Korean text)**을 코드(JavaScript, MyBatis XML 등)에 직접 기입합니다.
  - 예: `msg.success.save` → `"성공적으로 저장되었습니다."`

### 6.3 인프라 및 DB 연동
- **AS-IS:** 모놀리스 기반의 직접적인 엔티티 매핑
- **TO-BE:** 헥사고날 기반의 계층 격리 및 CQRS 적용
- **데이터 흐름:** 
  - `Interface` (RestController) → `Application` (Query/Command) → `Domain` (Repository) → `Infrastructure` (MyBatis)

## 7. 최신 아키텍처 표준 (testproject 룰 반영)

`testproject`에서 검증된 전사 표준 룰을 `migproject`에도 동일하게 적용합니다.

### 7.1 표준 응답 규격 (`RspDto<T>`)
모든 RestController의 반환 타입은 `com.aas.common.utils.RspDto<T>`로 감싸야 합니다.
- **구조:** `{ success: boolean, data: T, meta: { total, count }, error: { code, message } }`
- **사용법:** `return RspDto.ok(data);` 또는 `return RspDto.fail(code, message);`

### 7.2 API 문서화 및 REST 규격
- **Swagger 적용:** 모든 컨트롤러와 메서드에 `@Tag`와 `@Operation` 어노테이션을 작성하여 API를 문서화합니다.
- **경로 표준화:** 모든 API 경로는 `/api/v1/`으로 시작하며, 명사 위주의 Resource 중심 설계를 지향합니다.
  - 예: `/api/v1/display/standardCategoryMgmt/getStandardCategoryMgmt.do`
- **Mapping 어노테이션:** `@RequestMapping` 대신 `@GetMapping`, `@PostMapping` 등을 사용하여 의도를 명확히 합니다.

### 7.3 MapStruct 매핑 정책
데이터 변환 시 필드 불일치로 인한 오류 및 경고를 방지하기 위해 다음 설정을 필수 적용합니다.
- **정책:** `@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)`
- **효과:** 매핑되지 않은 타겟 프로퍼티에 대해 빌드 타임 경고를 무시하고 런타임 안정성을 확보합니다.

### 7.4 명령 객체(Command)의 분리
Interface 계층의 `CudDto`는 Application 계층으로 진입하기 전 반드시 `Command` 객체로 변환되어야 합니다.
- **수정 요청:** `CategoryCudDto` (Interface) -> `SaveCategoryCommand` (Application)
- **이유:** 외부 환경(Web)과 내부 비즈니스 로직(App)의 결합도를 낮추기 위함입니다.

---
> **[중요]** 테스트 시 모든 팝업과 그리드 데이터가 위 규칙에 따라 한글명이 정확히 표출되는지, 그리고 `RspDto`를 통해 데이터가 정상 수신되는지 확인하시기 바랍니다.

---

## 8. 마이그레이션 핵심 체크리스트 & 잦은 실수 방지 (Lessons Learned)

마이그레이션 과정에서 놓치기 쉬운 아키텍처 규칙과 리팩토링 버그를 방지하기 위해 다음 항목을 반드시 체크합니다.

### 8.1. API 및 쿼리 재사용 확인 (중복 개발 방지)
- **증상:** 기존 레거시에서는 두 개의 화면에서 똑같은 데이터를 다른 API로 불러왔을 수 있습니다.
- **방지 대책:** 새로운 기능(예: 좌측 트리)을 만들기 전에, 이미 프로젝트에 만들어진 범용 기능(예: `getStandardCategoryMgmt.do`)이 있는지 먼저 확인하고 **적극적으로 재사용(Reuse)** 합니다.

### 8.2. Domain Repository의 `@Mapper` 및 Bean 등록 누락 주의
- **증상:** Application 기동 시 `Parameter 0 of constructor... required a bean of type... that could not be found.` 에러 발생.
- **방지 대책:** 헥사고날 아키텍처 원칙 상 Domain 인터페이스에 MyBatis 어노테이션(`@Mapper`)을 달지 않습니다. 반드시 `Infrastructure` 레이어에 `SqlSessionTemplate`을 사용하는 `*Impl` 클래스를 만들고 **`@Repository`를 명시적으로 등록**하여 빈(Bean)이 연결되게 해야 합니다.

### 8.3. 로직 제거 시 미사용 Import 및 구문(Syntax) 클린업
- **증상:** 불필요해진 DTO나 함수 삭제 과정에서 안 쓰는 `import` 패키지가 찌꺼기로 남거나, 괄호가 남아 컴파일 에러 유발.
- **방지 대책:** 객체나 서비스 함수를 들어낼 경우 상단의 `import` 영역 확인을 생활화하고, 항상 재빌드(Compile)를 돌려 구문(Syntax Error) 무결성을 검증합니다.

### 8.4. SQL 마이그레이션 필수 체크리스트 (다국어 및 컬럼 표준)
- **증상:** 데이터베이스 스키마 전환 시 존재하지 않는 컬럼(`DISP_YN` 등)을 조회하거나, 물리적으로 분리된 다국어 명칭 정보를 누락하여 오류 유발.
- **방지 대책:** SQL 쿼리 마이그레이션 시 다음 3가지 수칙을 엄격히 준수합니다.
  1. **다국어 테이블(`*_ML`) 조인 필수**: 모든 카테고리명, 속성명 등 명칭(`_NM`) 정보는 기저 테이블이 아닌 별도의 `ML` 테이블(예: `PR_DISP_CTG_BASE_ML`)에 존재하므로 반드시 `JOIN` 처리합니다.
  2. **언어 코드(`LANG_CD`) 고정**: 다국어 조인 시 `ON` 절 또는 `WHERE` 절에 **`LANG_CD = 'ko'`** 조건을 명시하여 한국어 데이터를 기준으로 조회합니다.
  3. **표준 컬럼 매핑 확인**: 전시기준의 `DISP_YN`은 실제 DB 스키마에서 **`USE_YN`**으로 정의되어 있는 경우가 많습니다. 또한 삭제된 데이터를 제외하기 위해 **`DEL_YN = 'N'`** 조건을 누락하지 않아야 합니다.
  4. **AS-IS 출처 명시 (Traceability)**: 마이그레이션된 모든 MyBatis XML 쿼리 상단에 **AS-IS 원본 파일 경로 및 쿼리 ID를 주석으로 반드시 기입**하여 역추적이 가능하도록 합니다.
  5. **계층 구조 명칭 추출 확인**: 단순 명칭 조인이 아닌, 원본에서 대>중>소>세와 같은 **계층 경로(Hierarchy)를 추출하는 서브쿼리**가 있는지 확인하고, 누락 없이 마이그레이션해야 합니다.
  6. **조회 컬럼 1:1 완전 이행**: 원본 AS-IS 쿼리에서 조회(`SELECT`)하는 모든 컬럼은 임의로 축소하지 말고 **1:1로 모두 이행**해야 합니다. 데이터 활용 계획과 관계없이 원본 사양 유지를 원칙으로 합니다.

> [!IMPORTANT]
> **MyBatis Alias 주의:** DTO 필드명이 `dispYn`일지라도 DB 컬럼이 `USE_YN`이라면 `A.USE_YN AS dispYn`과 같이 별칭을 사용하여 정확히 매핑해야 합니다.



