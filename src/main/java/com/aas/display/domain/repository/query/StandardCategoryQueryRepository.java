package com.aas.display.domain.repository.query;

import com.aas.display.domain.repository.query.param.StandardCategoryMgmtQueryParam;
import com.aas.display.domain.repository.query.result.*;
import java.util.List;
import java.util.Map;

/**
 * 표준 카테고리 조회 인터페이스 (원본 복구)
 */
public interface StandardCategoryQueryRepository {
    List<StandardCategoryMgmtQueryResult> getPrStdCtgListWithHierarchy();
    StandardCategoryMgmtQueryResult getStandardCategoryInfo(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryMgmtChildListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryMgmtQueryResult> getStandardCategoryMgmtChildList(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryMgmtGoodsListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryGoodsQueryResult> getStandardCategoryMgmtGoodsList(StandardCategoryMgmtQueryParam param);
    int getStandardCategoryGoodsAttrListCount(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryAttrQueryResult> getStandardCategoryGoodsAttrList(StandardCategoryMgmtQueryParam param);
    List<StandardCategoryAttrQueryResult> getAttInfoList(Map<String, Object> param);
}
