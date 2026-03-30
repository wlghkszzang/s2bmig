package com.aas.display.application.queryservice;

import com.aas.display.application.queryservice.query.GetStandardCategoryQuery;
import com.aas.display.application.queryservice.query.response.*;
import com.aas.display.application.transfer.StandardCategoryAppTransfer;
import com.aas.display.domain.repository.query.StandardCategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StandardCategoryQueryService {

    private final StandardCategoryQueryRepository queryRepository;
    private final StandardCategoryAppTransfer appTransfer;

    public List<StandardCategoryQueryResponse> getStandardCategoryTree() {
        return appTransfer.toQueryResponseList(queryRepository.getPrStdCtgListWithHierarchy());
    }

    public StandardCategoryQueryResponse getStandardCategoryInfo(GetStandardCategoryQuery query) {
        return appTransfer.toQueryResponse(queryRepository.getStandardCategoryInfo(appTransfer.toQueryParam(query)));
    }

    public int getChildListCount(GetStandardCategoryQuery query) {
        return queryRepository.getStandardCategoryMgmtChildListCount(appTransfer.toQueryParam(query));
    }

    public List<StandardCategoryQueryResponse> getChildList(GetStandardCategoryQuery query) {
        return appTransfer.toQueryResponseList(queryRepository.getStandardCategoryMgmtChildList(appTransfer.toQueryParam(query)));
    }

    public int getGoodsListCount(GetStandardCategoryQuery query) {
        return queryRepository.getStandardCategoryMgmtGoodsListCount(appTransfer.toQueryParam(query));
    }

    public List<StandardCategoryGoodsQueryResponse> getGoodsList(GetStandardCategoryQuery query) {
        return appTransfer.toGoodsQueryResponseList(queryRepository.getStandardCategoryMgmtGoodsList(appTransfer.toQueryParam(query)));
    }

    public int getAttrListCount(GetStandardCategoryQuery query) {
        return queryRepository.getStandardCategoryGoodsAttrListCount(appTransfer.toQueryParam(query));
    }

    public List<StandardCategoryAttrQueryResponse> getAttrList(GetStandardCategoryQuery query) {
        return appTransfer.toAttrQueryResponseList(queryRepository.getStandardCategoryGoodsAttrList(appTransfer.toQueryParam(query)));
    }

    public List<StandardCategoryAttrQueryResponse> getAttInfoList(Map<String, Object> param) {
        return appTransfer.toAttrQueryResponseList(queryRepository.getAttInfoList(param));
    }
}
