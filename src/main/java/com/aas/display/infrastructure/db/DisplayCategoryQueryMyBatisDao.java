package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.query.param.DisplayCategoryQueryParam;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import com.aas.display.domain.repository.query.result.PrDpmlBaseQueryResult;
import com.aas.display.domain.repository.query.result.DisplayCategoryQueryResult;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface DisplayCategoryQueryMyBatisDao {
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
