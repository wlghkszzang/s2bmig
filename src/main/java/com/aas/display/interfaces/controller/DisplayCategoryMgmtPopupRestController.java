package com.aas.display.interfaces.controller;

import com.aas.display.application.queryservice.DisplayCategoryQueryService;
import com.aas.display.application.queryservice.query.GetDisplayCategoryQuery;
import com.aas.display.application.queryservice.query.response.DisplayCategoryQueryResponse;
import com.aas.display.domain.repository.query.result.PrDpmlBaseQueryResult;
import com.aas.common.utils.RspDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 전시 카테고리 팝업 데이터 처리 (Restored)
 */
@Tag(name = "전시 카테고리 팝업 데이터")
@RestController
@RequestMapping("/popup")
@RequiredArgsConstructor
public class DisplayCategoryMgmtPopupRestController {

    private final DisplayCategoryQueryService displayCategoryService;

    @Operation(summary = "전시 카테고리 목록 조회 (팝업용)", description = "전시 카테고리 선택 팝업에서 목록을 조회합니다.")
    @GetMapping("/displayCategoryMgmtPopup.getDisplayCategoryList.do")
    public RspDto<List<DisplayCategoryQueryResponse>> getDisplayCategoryList(GetDisplayCategoryQuery query) {
        if (query.getLangCd() == null || query.getLangCd().isEmpty()) {
            query.setLangCd("ko");
        }
        List<DisplayCategoryQueryResponse> list = displayCategoryService.getSubCategoryList(query);
        return RspDto.ok(list);
    }

    @Operation(summary = "전시 카테고리 Tree 리스트 조회", description = "전시 카테고리 선택 팝업에서 사용하는 트리 데이터를 조회합니다.")
    @GetMapping("/displayCategoryMgmtPopup.displayCategoryTreeList.do")
    public RspDto<List<DisplayCategoryQueryResponse>> getDisplayCategoryTreeList(GetDisplayCategoryQuery query) {
        if (query.getLangCd() == null || query.getLangCd().isEmpty()) {
            query.setLangCd("ko"); // Default to Korean
        }
        List<DisplayCategoryQueryResponse> list = displayCategoryService.getCategoryTreeList(query);
        return RspDto.ok(list);
    }

    @Operation(summary = "전시몰 목록 조회", description = "특정 사이트에 속한 전시몰 목록을 조회합니다.")
    @GetMapping("/displayCategoryMgmtPopup.getMallList.do")
    public RspDto<List<PrDpmlBaseQueryResult>> getMallList(String siteNo) {
        List<PrDpmlBaseQueryResult> list = displayCategoryService.getMallList(siteNo);
        return RspDto.ok(list);
    }
}
