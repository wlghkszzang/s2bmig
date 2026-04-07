package com.aas.display.interfaces.controller;

import com.aas.display.application.queryservice.DisplayCategoryQueryService;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import com.aas.display.domain.repository.query.result.PrDpmlBaseQueryResult;
import com.aas.display.interfaces.dto.req.DisplayCategoryPopupReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

/**
 * 전시 카테고리 팝업 컨트롤러 (Restored)
 */
@Tag(name = "전시 카테고리 팝업")
@Controller
@RequiredArgsConstructor
public class DisplayCategoryMgmtPopupController {

    private final DisplayCategoryQueryService displayCategoryService;

    @Operation(summary = "전시 카테고리 조회 팝업 호출 (Flat)", description = "전시 카테고리 선택 팝업(그리드)을 호출합니다.")
    @GetMapping("/display/displayCategoryMgmtPopup/displayCategoryListPopup.do")
    public String displayCategoryListPopup(Model model, @ModelAttribute("requestData") DisplayCategoryPopupReqDto requestData) {
        requestData.syncArgs();
        List<CcSiteBaseQueryResult> siteList = displayCategoryService.getSiteList();
        List<PrDpmlBaseQueryResult> mallList = null;
        if (requestData.getArgSiteNo() != null) {
            mallList = displayCategoryService.getMallList(requestData.getArgSiteNo());
        } else if (siteList != null && !siteList.isEmpty()) {
            mallList = displayCategoryService.getMallList(siteList.get(0).getSiteNo());
        }
        model.addAttribute("siteList", siteList);
        model.addAttribute("mallList", mallList);
        model.addAttribute("requestData", requestData);
        return "views/popup/displayCategoryListPopup";
    }

    @Operation(summary = "전시 카테고리 Tree 조회 팝업 호출", description = "전시 카테고리 선택 팝업(트리)을 호출합니다.")
    @GetMapping("/display/displayCategoryMgmtPopup/displayCategoryTreeListPopup.do")
    public String displayCategoryTreeListPopup(Model model, @ModelAttribute("requestData") DisplayCategoryPopupReqDto requestData) {
        requestData.syncArgs();
        List<CcSiteBaseQueryResult> siteList = displayCategoryService.getSiteList();
        
        model.addAttribute("argSelectType", requestData.getArgSelectType());
        model.addAttribute("siteList", siteList);
        model.addAttribute("request", requestData);
        
        return "views/popup/displayCategoryTreeListPopup";
    }
}
