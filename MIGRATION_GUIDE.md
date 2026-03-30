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

레거시 시스템에서 신규 프로젝트로 전환 시 다음의 기술적 변경 사항을 반드시 준수합니다.

### 6.1 그리드 컴포넌트 전환
- **AS-IS:** `RealGrid`
- **TO-BE:** `AUIGrid`
- **주요 차이점:**
  - 데이터 변경 감지: `getAddedRowItems()`, `getEditedRowItems()`, `getRemovedItems()` 등을 사용하여 변경분만 서버로 전송합니다.
  - 그리드 초기화 시 `standardCategoryMgmt.js`와 같은 모듈화된 JS 패턴을 사용합니다.

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
