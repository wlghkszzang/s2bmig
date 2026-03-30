package com.aas.goods.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OptionMgmtPopupController {

    private final com.aas.system.application.queryservice.CodeQueryService codeService;

    @GetMapping("/popup/goods/optionMgmt.optinPopup.do")
    public String optionPopupView(Model model, 
                                  @RequestParam(required = false) String entrNo, 
                                  @RequestParam(required = false) String optnCatNo) {
        
        // 권한 분기는 로그인 세션 보안 설정 완료 후 추가 예정
        model.addAttribute("entrNo", entrNo);
        model.addAttribute("optnCatNo", optnCatNo);
        
        // 공통 코드 (PR018: 사용처, PR019: 분류유형) 조회 및 뷰 주입
        model.addAttribute("codeList", codeService.getStStdCd("PR018,PR019"));
        
        return "views/popup/optionPopup";
    }
}
