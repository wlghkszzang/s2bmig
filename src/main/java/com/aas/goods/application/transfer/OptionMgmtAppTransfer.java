package com.aas.goods.application.transfer;

import com.aas.goods.application.commandservice.command.*;
import com.aas.goods.application.queryservice.query.GetOptionListQuery;
import com.aas.goods.application.queryservice.query.OptionListQueryResponse;
import com.aas.goods.domain.model.PrOptnCd;
import com.aas.goods.domain.model.PrOptnClssCd;
import com.aas.goods.domain.repository.query.param.OptionMgmtQueryParam;
import com.aas.goods.domain.repository.query.result.OptionMgmtQueryResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OptionMgmtAppTransfer {

    // [옵션 분류] CommandData -> Model
    @Mapping(target = "sysRegId", ignore = true)
    @Mapping(target = "sysModId", ignore = true)
    PrOptnClssCd toCategoryEntity(SaveOptionCategoryCommandData command);
    List<PrOptnClssCd> toCategoryModelList(List<SaveOptionCategoryCommandData> commands); // Match service
    default List<PrOptnClssCd> toCategoryEntityList(List<SaveOptionCategoryCommandData> commands) {
        return toCategoryModelList(commands);
    }

    // [옵션 상세코드] CommandData -> Model
    @Mapping(target = "sysRegId", ignore = true)
    @Mapping(target = "sysModId", ignore = true)
    PrOptnCd toCodeEntity(SaveOptionCodeCommandData command);
    List<PrOptnCd> toCodeModelList(List<SaveOptionCodeCommandData> commands); // Match service
    default List<PrOptnCd> toCodeEntityList(List<SaveOptionCodeCommandData> commands) {
        return toCodeModelList(commands);
    }

    // [Query -> Param]
    OptionMgmtQueryParam toQueryParam(GetOptionListQuery query);

    // [Result -> AppResponse]
    OptionListQueryResponse toQueryResponse(OptionMgmtQueryResult result);
    List<OptionListQueryResponse> toQueryResponseList(List<OptionMgmtQueryResult> resultList);
}
