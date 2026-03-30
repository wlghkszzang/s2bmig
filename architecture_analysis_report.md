# migproject vs testproject 아키텍처 및 규칙 분석

본 리포트는 `C:\S2B\testproject`와 현재 진행 중인 `migproject`를 비교 분석하여, `migproject`에서 보완해야 할 규칙(Rule)과 패턴을 제안합니다.

## 1. 주요 차이점 요약

| 항목 | testproject (권장 룰) | migproject (현상태) | 비고 |
| :--- | :--- | :--- | :--- |
| **공통 응답 (RspDto)** | `RspDto<T>`를 통한 전사 표준 규격 준수 | `ResponseEntity` 또는 직접 객체 반환 | 응답 일관성 및 에러 처리 표준화 필요 |
| **데이터 변환 계층** | Interface, Application, Infrastructure별 전용 Transfer 존재 | 일부 계층에서 혼용 또는 Entity 직접 사용 | 계층 간 디커플링(Decoupling) 강화 필요 |
| **도메인 모델 (Model)** | Entity와 분리된 순수 `Domain Model` 활용 | 주로 DB 중심의 `Entity` 직접 활용 | 비즈니스 로직의 순수성 확보 부족 |
| **API 문서화** | Swagger v3 어노테이션 (`@Tag`, `@Operation`) 필수 적용 | 일부 컨트롤러에 누락 또는 간소화 | API 가독성 및 가이드 자동화 필요 |
| **입력값 검증** | `jakarta.validation` (`@Valid`) 활용 | 명시적인 Validation 로직 부족 | 안전한 API 인터페이스 보장 필요 |

---

## 2. migproject에 도입 제안하는 강화된 룰 (Action Items)

### [Rule 1] 표준 응답 규격 `RspDto<T>` 도입
- 모든 REST API는 `RspDto.ok(data)` 또는 `RspDto.fail(code, message)` 형태로 반환해야 합니다.
- **구조**:
  - `success`: boolean
  - `data`: T
  - `error`: { code, message, details }
  - `meta`: { count, total } (페이징용)

### [Rule 2] 명령(Command) 객체의 적극 활용
- `interfaces` 계층에서 수신한 `ReqDto`는 즉시 `Command` 객체로 변환되어 `Application Service`로 전달되어야 합니다.
- `Application Layer`는 외부(DTO)의 변화에 영향을 받지 않아야 합니다.

### [Rule 3] 도메인 모델(Domain Model)과 엔티티(Entity)의 분리
- DB 테이블 구조인 `Entity`와 서비스 로직에서 사용하는 `Model`을 분리합니다.
- 복잡한 비즈니스 정책은 `Entity`가 아닌 `Model` 내부에 캡슐화합니다.

### [Rule 4] Swagger 문서화 표준화
- 모든 컨트롤러 클래스에 `@Tag`를 적용하고, 각 메서드에 `@Operation`을 사용하여 API 명세의 완성도를 높입니다.

### [Rule 5] 데이터 변환기(Transfer)의 분리 운영
- **DtoTransfer**: ReqDtop <-> Command
- **AppTransfer**: Command <-> Model
- **DomainMapper**: Model <-> Entity

---

## 3. 향후 마이그레이션 방향성

전시 카테고리 관리(Display Category Management) 모듈의 남은 기능 구현 시 위 규칙들을 시범 적용하여 프로젝트 전반의 코드 품질을 상향 평준화할 것을 권장합니다.
