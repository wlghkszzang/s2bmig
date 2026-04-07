package com.aas.display.application.transfer;

import com.aas.display.application.queryservice.query.GetStandardCategoryQuery;
import com.aas.display.application.queryservice.query.response.*;
import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardCategoryAppTransfer {
    StandardCategoryMgmtQueryParam toQueryParam(GetStandardCategoryQuery query);
    StandardCategoryQueryResponse toQueryResponse(StandardCategoryMgmtQueryResult result);
    List<StandardCategoryQueryResponse> toQueryResponseList(List<StandardCategoryMgmtQueryResult> resultList);
    StandardCategoryGoodsQueryResponse toGoodsQueryResponse(StandardCategoryGoodsQueryResult result);
    List<StandardCategoryGoodsQueryResponse> toGoodsQueryResponseList(List<StandardCategoryGoodsQueryResult> resultList);
    StandardCategoryAttrQueryResponse toAttrQueryResponse(StandardCategoryAttrQueryResult result);
    List<StandardCategoryAttrQueryResponse> toAttrQueryResponseList(List<StandardCategoryAttrQueryResult> resultList);
}