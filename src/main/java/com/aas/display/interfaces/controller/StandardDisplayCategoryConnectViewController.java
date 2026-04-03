package com.aas.display.interfaces.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/display/standardDisplayCategoryConnect")
@RequiredArgsConstructor
public class StandardDisplayCategoryConnectViewController {

    @GetMapping("/standardDisplayCategoryConnectView.do")
    public String standardDisplayCategoryConnectView(Model model) {
        // 필요시 기초 코드 데이터 세팅
        return "views/display/standardDisplayCategoryConnectView";
    }
}
