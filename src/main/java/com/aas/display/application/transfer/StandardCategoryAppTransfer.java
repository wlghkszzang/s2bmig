package com.aas.display.application.transfer;

import com.aas.display.application.commandservice.command.*;
import com.aas.display.domain.entity.PrStdCtg;
import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.StandardCategoryMgmtQueryResult;
import com.aas.display.interfaces.dto.req.StandardCategoryMgmtReqDto;
import com.aas.display.interfaces.dto.rsp.StandardCategoryMgmtRspDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardCategoryAppTransfer {
    PrStdCtg toEntity(SaveStandardCategoryCommand command);
    List<PrStdCtg> toEntityList(List<SaveStandardCategoryCommand> commands);
    @Mapping(target = "sysRegId", ignore = true)
    PrStdCtg.GoodsAttr toGoodsAttr(SaveStandardCategoryAttrCommand command);
    List<PrStdCtg.GoodsAttr> toGoodsAttrList(List<SaveStandardCategoryAttrCommand> commands);
    StandardCategoryMgmtQueryParam toParam(StandardCategoryMgmtReqDto reqDto);
    StandardCategoryMgmtRspDto toRsp(StandardCategoryMgmtQueryResult result);
    List<StandardCategoryMgmtRspDto> toRspList(List<StandardCategoryMgmtQueryResult> resultList);
}
