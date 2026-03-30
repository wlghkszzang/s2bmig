package com.aas.display.application.transfer;

import com.aas.display.application.commandservice.command.*;
import com.aas.display.application.queryservice.query.GetDisplayCategoryQuery;
import com.aas.display.application.queryservice.query.response.DisplayCategoryQueryResponse;
import com.aas.display.domain.entity.PrDispCtg;
import com.aas.display.domain.repository.query.param.DisplayCategoryQueryParam;
import com.aas.display.domain.repository.query.result.DisplayCategoryQueryResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DisplayCategoryAppTransfer {

    // [Command -> Entity]
    @Mapping(target = "sysRegDtm", ignore = true)
    @Mapping(target = "sysModDtm", ignore = true)
    PrDispCtg toEntity(SaveDisplayCategoryCommand command);
    List<PrDispCtg> toEntityList(List<SaveDisplayCategoryCommand> commands);

    // [Goods Command -> GoodsInfo Entity]
    PrDispCtg.GoodsInfo toGoodsInfo(SaveDisplayGoodsGridCommand.SaveDisplayGoodsCommand command);
    List<PrDispCtg.GoodsInfo> toGoodsInfoList(List<SaveDisplayGoodsGridCommand.SaveDisplayGoodsCommand> commands);

    // [Query -> Param]
    DisplayCategoryQueryParam toQueryParam(GetDisplayCategoryQuery query);

    // [Result -> AppResponse]
    DisplayCategoryQueryResponse toQueryResponse(DisplayCategoryQueryResult result);
    List<DisplayCategoryQueryResponse> toQueryResponseList(List<DisplayCategoryQueryResult> results);

    DisplayCategoryQueryResponse.GoodsInfoQueryResponse toGoodsQueryResponse(DisplayCategoryQueryResult.GoodsInfoResult result);
    List<DisplayCategoryQueryResponse.GoodsInfoQueryResponse> toGoodsQueryResponseList(List<DisplayCategoryQueryResult.GoodsInfoResult> results);
}
