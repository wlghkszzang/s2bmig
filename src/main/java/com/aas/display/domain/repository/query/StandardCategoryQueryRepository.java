package com.aas.display.domain.repository.query;

import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.StandardCategoryAttrQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryGoodsQueryResult;
import com.aas.display.domain.repository.query.result.StandardCategoryMgmtQueryResult;

import java.util.List;
import java.util.Map;

public interface StandardCategoryQueryRepository {
    List<StandardCategoryMgmtQueryResult> getPrStdCtgListWithHierarchy();
    StandardCategoryMgmtQueryResult getStandardCategoryInfo(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryMgmtChildListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryMgmtQueryResult> getStandardCategoryMgmtChildList(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryMgmtGoodsListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryGoodsQueryResult> getStandardCategoryMgmtGoodsList(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryGoodsAttrListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryAttrQueryResult> getStandardCategoryGoodsAttrList(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryDisplayListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryAttrQueryResult> getAttInfoList(Map<String, Object> param);
}
