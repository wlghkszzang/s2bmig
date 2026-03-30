package com.aas.display.interfaces.controller.popup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import com.aas.system.application.queryservice.CodeQueryService;

/**
 * 표준 카테고리 팝업 Controller
 */
@Controller
@RequiredArgsConstructor
public class StandardCategoryPopupController {

    private final CodeQueryService codeQueryService;

    /**
     * 속성 조회 팝업 호출
     */
    @GetMapping("/popup/standardCategory.attCdInfoPopup.do")
    public String attCdInfoPopup(Model model) {
        // 속성타입코드(PR043)
        model.addAttribute("codeList", codeQueryService.getStStdCd("PR043"));
        return "views/popup/display/attCdInfoPopup";
    }
}
