package com.aas.display.infrastructure.db.transfer;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 인프라 계층용 Mapping (StandardCategory)
 * 현재는 MyBatis 직접 매핑 사용 중이므로 구조적 자리 확보를 위한 Placeholder.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardCategoryDbTransfer {
}
