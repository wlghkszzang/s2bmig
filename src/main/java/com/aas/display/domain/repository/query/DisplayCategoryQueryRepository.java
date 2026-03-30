package com.aas.display.domain.repository.query;

import com.aas.display.domain.repository.query.param.DisplayCategoryQueryParam;
import com.aas.display.domain.repository.query.result.DisplayCategoryQueryResult;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import com.aas.display.domain.repository.query.result.PrDpmlBaseQueryResult;
import java.util.List;

public interface DisplayCategoryQueryRepository {
    List<DisplayCategoryQueryResult> getCategoryTreeList(DisplayCategoryQueryParam search);
    DisplayCategoryQueryResult getCategoryDetail(DisplayCategoryQueryParam search);
    int getSubCategoryListCount(DisplayCategoryQueryParam search);
    List<DisplayCategoryQueryResult> getSubCategoryList(DisplayCategoryQueryParam search);
    int getDisplayGoodsListCount(DisplayCategoryQueryParam search);
    List<DisplayCategoryQueryResult.GoodsInfoResult> getDisplayGoodsList(DisplayCategoryQueryParam search);
    List<DisplayCategoryQueryResult.GoodsInfoResult> getGoodsListDetail(DisplayCategoryQueryParam search);
    List<String> getCheckPrDispGoodsInfo(String dispCtgNo);
    String getCheckValidGoods(DisplayCategoryQueryParam search);
    List<CcSiteBaseQueryResult> getSiteList();
    List<PrDpmlBaseQueryResult> getMallList(String siteNo);
}
