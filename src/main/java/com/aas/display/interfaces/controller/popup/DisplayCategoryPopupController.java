package com.aas.display.interfaces.controller.popup;

import com.aas.display.application.queryservice.DisplayCategoryQueryService;
import com.aas.display.domain.repository.query.result.CcSiteBaseQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * ?꾩떆 移댄뀒怨좊━ 愿由??앹뾽 Controller
 */
@Controller
@RequiredArgsConstructor
public class DisplayCategoryPopupController {

    private final DisplayCategoryQueryService queryService;

    /**
     * ?꾩떆 移댄뀒怨좊━ 議고쉶 ?붾㈃ ?몄텧
     */
    @GetMapping("/popup/displayCategoryMgmtPopup.displayCategoryListPopup.do")
    public String displayCategoryListPopup(Model model, 
                                           @RequestParam(required = false) String argSelectType,
                                           @RequestParam(required = false) String argSiteNo,
                                           @RequestParam(required = false) String argDpmlNo,
                                           @RequestParam(required = false) String argUseYn) {
        
        List<CcSiteBaseQueryResult> siteList = queryService.getSiteList();
        model.addAttribute("siteList", siteList);

        if (siteList != null && !siteList.isEmpty()) {
            String targetSiteNo = (argSiteNo != null && !argSiteNo.isEmpty()) ? argSiteNo : siteList.getFirst().getSiteNo();
            model.addAttribute("mallList", queryService.getMallList(targetSiteNo));
        }

        model.addAttribute("argSelectType", argSelectType != null ? argSelectType : "N");
        model.addAttribute("argSiteNo", argSiteNo != null ? argSiteNo : "");
        model.addAttribute("argDpmlNo", argDpmlNo != null ? argDpmlNo : "");
        model.addAttribute("argUseYn", argUseYn != null ? argUseYn : "");

        return "views/popup/display/displayCategoryListPopupView";
    }
}
