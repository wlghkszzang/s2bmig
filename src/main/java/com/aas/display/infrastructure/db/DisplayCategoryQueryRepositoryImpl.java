package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.query.DisplayCategoryQueryRepository;
import com.aas.display.domain.repository.query.param.DisplayCategoryQueryParam;
import com.aas.display.domain.repository.query.result.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DisplayCategoryQueryRepositoryImpl implements DisplayCategoryQueryRepository {
    private final DisplayCategoryQueryMyBatisDao mapper;

    @Override
    public List<DisplayCategoryQueryResult> getCategoryTreeList(DisplayCategoryQueryParam param) {
        return mapper.getCategoryTreeList(param);
    }

    @Override
    public DisplayCategoryQueryResult getCategoryDetail(DisplayCategoryQueryParam param) {
        return mapper.getCategoryDetail(param);
    }

    @Override
    public int getSubCategoryListCount(DisplayCategoryQueryParam param) {
        return mapper.getSubCategoryListCount(param);
    }

    @Override
    public List<DisplayCategoryQueryResult> getSubCategoryList(DisplayCategoryQueryParam param) {
        return mapper.getSubCategoryList(param);
    }

    @Override
    public int getDisplayGoodsListCount(DisplayCategoryQueryParam param) {
        return mapper.getDisplayGoodsListCount(param);
    }

    @Override
    public List<DisplayCategoryQueryResult.GoodsInfoResult> getDisplayGoodsList(DisplayCategoryQueryParam param) {
        return mapper.getDisplayGoodsList(param);
    }

    @Override
    public List<DisplayCategoryQueryResult.GoodsInfoResult> getGoodsListDetail(DisplayCategoryQueryParam param) {
        return mapper.getGoodsListDetail(param);
    }

    @Override
    public List<String> getCheckPrDispGoodsInfo(String dispCtgNo) {
        return mapper.getCheckPrDispGoodsInfo(dispCtgNo);
    }

    @Override
    public String getCheckValidGoods(DisplayCategoryQueryParam param) {
        return mapper.getCheckValidGoods(param);
    }

    @Override
    public List<CcSiteBaseQueryResult> getSiteList() {
        return mapper.getSiteList();
    }

    @Override
    public List<PrDpmlBaseQueryResult> getMallList(String siteNo) {
        return mapper.getMallList(siteNo);
    }
}