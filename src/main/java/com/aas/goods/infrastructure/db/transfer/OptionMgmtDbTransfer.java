package com.aas.goods.infrastructure.db.transfer;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 인프라 계층용 Mapping (OptionMgmt)
 * DB Entity <-> Domain Model 변환을 담당함.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OptionMgmtDbTransfer {
}
