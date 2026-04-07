package com.aas.display.domain.repository.query;

import com.aas.display.domain.repository.query.param.DisplayCategoryQueryParam;
import com.aas.display.domain.repository.query.result.*;
import java.util.List;

/**
 * 전시 카테고리 조회 인터페이스 (원본 복구)
 */
public interface DisplayCategoryQueryRepository {
    List<DisplayCategoryQueryResult> getCategoryTreeList(DisplayCategoryQueryParam param);
    DisplayCategoryQueryResult getCategoryDetail(DisplayCategoryQueryParam param);
    int getSubCategoryListCount(DisplayCategoryQueryParam param);
    List<DisplayCategoryQueryResult> getSubCategoryList(DisplayCategoryQueryParam param);
    int getDisplayGoodsListCount(DisplayCategoryQueryParam param);
    List<DisplayCategoryQueryResult.GoodsInfoResult> getDisplayGoodsList(DisplayCategoryQueryParam param);
    List<DisplayCategoryQueryResult.GoodsInfoResult> getGoodsListDetail(DisplayCategoryQueryParam param);
    List<String> getCheckPrDispGoodsInfo(String dispCtgNo);
    String getCheckValidGoods(DisplayCategoryQueryParam param);
    List<CcSiteBaseQueryResult> getSiteList();
    List<PrDpmlBaseQueryResult> getMallList(String siteNo);
}
