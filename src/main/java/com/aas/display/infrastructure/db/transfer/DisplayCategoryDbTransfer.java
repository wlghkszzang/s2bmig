package com.aas.display.infrastructure.db.transfer;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 인프라 계층용 Mapping (DB Entity <-> Domain Model)
 * 현재는 MyBatis가 Domain 전용 Result(QueryResult) 객체에 직접 매핑하므로 빈 구현체로 시작함.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DisplayCategoryDbTransfer {
    // 향후 DB 전용 Entity 도입 시 여기에 매핑 로직 추가
}
