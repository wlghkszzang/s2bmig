package com.aas.display.interfaces.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/display/standardCategoryMgmt")
@RequiredArgsConstructor
public class StandardCategoryViewController {

    private final com.aas.system.application.queryservice.CodeQueryService codeService;

    @GetMapping("/standardCategoryMgmtView.do")
    public String standardCategoryMgmtView(Model model) {
        // 속성타입코드(PR043)
        model.addAttribute("codeList", codeService.getStStdCd("PR043"));
        return "views/display/standardCategoryMgmtView";
    }
}
