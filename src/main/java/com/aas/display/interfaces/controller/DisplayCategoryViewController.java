package com.aas.display.interfaces.controller;

import com.aas.display.application.queryservice.DisplayCategoryQueryService;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
 * 전시 카테고리 뷰 컨트롤러
 */
@Controller
@RequestMapping("/display/displayCategoryMgmt")
@RequiredArgsConstructor
public class DisplayCategoryViewController {

    private final DisplayCategoryQueryService queryService;

    @GetMapping("/displayCategoryMgmtView.do")
    public String displayCategoryMgmtView(Model model) {
        List<CcSiteBaseQueryResult> siteList = queryService.getSiteList();
        model.addAttribute("siteList", siteList);

        if (siteList != null && !siteList.isEmpty()) {
            String firstSiteNo = siteList.getFirst().getSiteNo();
            model.addAttribute("mallList", queryService.getMallList(firstSiteNo));
        }

        return "views/display/displayCategoryMgmtView";
    }
}
