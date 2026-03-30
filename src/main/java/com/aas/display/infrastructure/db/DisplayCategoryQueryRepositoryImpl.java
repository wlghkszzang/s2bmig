package com.aas.display.infrastructure.db;

import com.aas.display.domain.repository.query.DisplayCategoryQueryRepository;
import com.aas.display.domain.repository.query.param.DisplayCategoryQueryParam;
import com.aas.display.domain.repository.query.result.DisplayCategoryQueryResult;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import com.aas.display.domain.repository.query.result.PrDpmlBaseQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DisplayCategoryQueryRepositoryImpl implements DisplayCategoryQueryRepository {

    private final DisplayCategoryQueryMyBatisDao mapper;

    @Override
    public List<DisplayCategoryQueryResult> getCategoryTreeList(DisplayCategoryQueryParam search) {
        return mapper.getCategoryTreeList(search);
    }

    @Override
    public DisplayCategoryQueryResult getCategoryDetail(DisplayCategoryQueryParam search) {
        return mapper.getCategoryDetail(search);
    }

    @Override
    public int getSubCategoryListCount(DisplayCategoryQueryParam search) {
        return mapper.getSubCategoryListCount(search);
    }

    @Override
    public List<DisplayCategoryQueryResult> getSubCategoryList(DisplayCategoryQueryParam search) {
        return mapper.getSubCategoryList(search);
    }

    @Override
    public int getDisplayGoodsListCount(DisplayCategoryQueryParam search) {
        return mapper.getDisplayGoodsListCount(search);
    }

    @Override
    public List<DisplayCategoryQueryResult.GoodsInfoResult> getDisplayGoodsList(DisplayCategoryQueryParam search) {
        return mapper.getDisplayGoodsList(search);
    }

    @Override
    public List<DisplayCategoryQueryResult.GoodsInfoResult> getGoodsListDetail(DisplayCategoryQueryParam search) {
        return mapper.getGoodsListDetail(search);
    }

    @Override
    public List<String> getCheckPrDispGoodsInfo(String dispCtgNo) {
        return mapper.getCheckPrDispGoodsInfo(dispCtgNo);
    }

    @Override
    public String getCheckValidGoods(DisplayCategoryQueryParam search) {
        return mapper.getCheckValidGoods(search);
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
