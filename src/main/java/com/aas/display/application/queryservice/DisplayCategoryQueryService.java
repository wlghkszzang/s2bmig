package com.aas.display.application.queryservice;

import com.aas.display.application.queryservice.query.GetDisplayCategoryQuery;
import com.aas.display.application.queryservice.query.response.DisplayCategoryQueryResponse;
import com.aas.display.application.transfer.DisplayCategoryAppTransfer;
import com.aas.display.domain.repository.query.DisplayCategoryQueryRepository;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import com.aas.display.domain.repository.query.result.PrDpmlBaseQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 전시 카테고리 조회 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DisplayCategoryQueryService {

    private final DisplayCategoryQueryRepository queryRepository;
    private final DisplayCategoryAppTransfer appTransfer;

    public List<DisplayCategoryQueryResponse> getCategoryTreeList(GetDisplayCategoryQuery query) {
        return appTransfer.toQueryResponseList(queryRepository.getCategoryTreeList(appTransfer.toQueryParam(query)));
    }

    public DisplayCategoryQueryResponse getCategoryDetail(GetDisplayCategoryQuery query) {
        return appTransfer.toQueryResponse(queryRepository.getCategoryDetail(appTransfer.toQueryParam(query)));
    }

    public int getSubCategoryListCount(GetDisplayCategoryQuery query) {
        return queryRepository.getSubCategoryListCount(appTransfer.toQueryParam(query));
    }

    public List<DisplayCategoryQueryResponse> getSubCategoryList(GetDisplayCategoryQuery query) {
        return appTransfer.toQueryResponseList(queryRepository.getSubCategoryList(appTransfer.toQueryParam(query)));
    }

    public int getDisplayGoodsListCount(GetDisplayCategoryQuery query) {
        return queryRepository.getDisplayGoodsListCount(appTransfer.toQueryParam(query));
    }

    public List<DisplayCategoryQueryResponse.GoodsInfoQueryResponse> getDisplayGoodsList(GetDisplayCategoryQuery query) {
        return appTransfer.toGoodsQueryResponseList(queryRepository.getDisplayGoodsList(appTransfer.toQueryParam(query)));
    }

    public List<DisplayCategoryQueryResponse.GoodsInfoQueryResponse> getGoodsListDetail(GetDisplayCategoryQuery query) {
        return appTransfer.toGoodsQueryResponseList(queryRepository.getGoodsListDetail(appTransfer.toQueryParam(query)));
    }

    public List<String> getCheckPrDispGoodsInfo(String dispCtgNo) {
        return queryRepository.getCheckPrDispGoodsInfo(dispCtgNo);
    }

    public String getCheckValidGoods(GetDisplayCategoryQuery query) {
        return queryRepository.getCheckValidGoods(appTransfer.toQueryParam(query));
    }

    public List<CcSiteBaseQueryResult> getSiteList() {
        return queryRepository.getSiteList();
    }

    public List<PrDpmlBaseQueryResult> getMallList(String siteNo) {
        return queryRepository.getMallList(siteNo);
    }
}
