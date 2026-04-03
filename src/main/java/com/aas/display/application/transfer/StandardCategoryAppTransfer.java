package com.aas.display.application.transfer;

import com.aas.display.application.queryservice.query.GetStandardCategoryQuery;
import com.aas.display.application.queryservice.query.response.StandardCategoryAttrQueryResponse;
import com.aas.display.application.queryservice.query.response.StandardCategoryGoodsQueryResponse;
import com.aas.display.application.queryservice.query.response.StandardCategoryQueryResponse;
import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.StandardCategoryAttrQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryGoodsQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryMgmtQueryResult;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardCategoryAppTransfer {

    // Query -> Param
    StandardCategoryMgmtQueryParam toQueryParam(GetStandardCategoryQuery query);

    // Result -> AppResponse
    StandardCategoryQueryResponse toQueryResponse(StandardCategoryMgmtQueryResult result);
    List<StandardCategoryQueryResponse> toQueryResponseList(List<StandardCategoryMgmtQueryResult> resultList);

    StandardCategoryGoodsQueryResponse toGoodsQueryResponse(StandardCategoryGoodsQueryResult result);
    List<StandardCategoryGoodsQueryResponse> toGoodsQueryResponseList(List<StandardCategoryGoodsQueryResult> resultList);

    StandardCategoryAttrQueryResponse toAttrQueryResponse(StandardCategoryAttrQueryResult result);
    List<StandardCategoryAttrQueryResponse> toAttrQueryResponseList(List<StandardCategoryAttrQueryResult> resultList);
}
