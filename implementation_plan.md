# 전시 카테고리 관리 마이그레이션 구현 계획

## 1. 개요
전시 카테고리 관리(`DisplayCategoryMgmt`) 모듈을 AS-IS(`x2bee-bo`, `api-bo`)에서 `migproject`로 마이그레이션합니다. 
Hexagonal Architecture와 CQRS 패턴을 적용하여 코드 품질과 유지보수성을 확보합니다.

## 2. 기술 스택
- **Backend**: Java 21, Spring Boot 3.3.0, MyBatis, PostgreSQL
- **Frontend**: Vanilla JS, Axios, AUIGrid, zTree, HTML5, Vanilla CSS
- **Library**: MapStruct, Lombok

## 3. 이관 전략 (Hexagonal Architecture / CQRS)

### 3.1 Domain Layer
- **Entity**: `PrDispCtg`, `PrDispGoodsInfo`, `PrDispCtgMl` 등 기본 엔티티 생성

### 3.2 Application Layer
- **QueryService**: 트리 조회, 상세 조회, 목록 조회 로직 (Read Only)
- **CommandService**: 최상위 카테고리 추가, 기본 정보 수정, 그리드 데이터(카테고리/상품/다국어) 저장 로직 (Transactional)

### 3.3 Infrastructure Layer (Persistence)
- **QueryRepository**: `DisplayCategoryQueryRepository` 및 MyBatis 인터페이스
- **CommandRepository**: `DisplayCategoryCommandRepository` 및 MyBatis 인터페이스
- **MyBatis Mapper**:
    - `DisplayCategoryQueryMapper.xml`: 조회용 쿼리 (AS-IS `displayrodb` 기반)
    - `DisplayCategoryCommandMapper.xml`: CUD용 쿼리 (AS-IS `displayrwdb` 기반)

### 3.4 Interface Layer (Web)
- **Controller**:
    - `DisplayCategoryViewController`: 화면 이동 및 초기 데이터 로드
    - `DisplayCategoryRestController`: AJAX 통신용 API 엔드포인트
- **DTO**: `DisplayCategoryReqDto`, `DisplayCategoryRspDto`, `DisplayCategoryCudDto` 등 정의
- **Transfer**: MapStruct 기반 `DisplayCategoryTransfer` 인터페이스 구현

### 3.5 Frontend Layer
- **HTML**: `displayCategoryMgmtView.html` (기존 `displayCategoryMgmtView.html` 기반 레이아웃 최적화)
- **JavaScript**: `displayCategoryMgmt.js` (기존 `eventHandler.js`, `provider.js` 통합 및 AUIGrid 연동)
- **CSS**: `index.css`를 활용한 프리미엄 UI 디자인 적용

## 4. 마이그레이션 상세 단계

| 단계 | 작업 내용 | 비고 |
| :--- | :--- | :--- |
| **Step 1** | DTO 및 Entity 정의 | Lombok/MapStruct 설정 |
| **Step 2** | MyBatis Mapper & XML 작성 | Query/Command 분리 |
| **Step 3** | Persistence Repository 구현 | 4계층 구조 준수 |
| **Step 4** | Application Service 구현 | 트랜잭션 관리 |
| **Step 5** | REST/View Controller 구현 | 엔드포인트 매핑 |
| **Step 6** | HTML/JS UI 구현 및 연동 | AUIGrid/zTree 연동 |
| **Step 7** | 최종 테스트 및 빌드 오류 해결 | 통합 점검 |

## 5. 주의 사항
- **CQRS 엄격 준수**: 조회와 변경 쿼리 매퍼를 반드시 분리할 것.
- **MapStruct**: Entity <-> DTO 변환 시 적극 활용.
- **Aesthetics**: 디자인 가이드라인에 따라 프리미엄 UI 적용.
- **시퀀스**: `PR_DISP_CTG_BASE_SQ01` 연동 확인.
