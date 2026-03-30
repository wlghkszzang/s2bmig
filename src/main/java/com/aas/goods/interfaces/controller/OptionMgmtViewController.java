package com.aas.goods.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OptionMgmtViewController {

    private final com.aas.system.application.queryservice.CodeQueryService codeService;

    @GetMapping("/goods/optionMgmt.optionMgmtView.do")
    public String optionMgmtView(Model model) {
        // 공통 코드 조회 서비스 (실제 DB 연동, langCd='ko')
        model.addAttribute("codeList", codeService.getStStdCd("PR018,PR019"));
        // 업로드 도메인 추가 (에러 방지용 빈 값)
        model.addAttribute("uploadDomain", "");
        return "views/goods/optionMgmtView";
    }
}
