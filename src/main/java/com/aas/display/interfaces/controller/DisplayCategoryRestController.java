package com.aas.display.interfaces.controller;

import com.aas.common.utils.RspDto;
import com.aas.common.utils.RspDto.MetaResponse;
import com.aas.display.application.commandservice.DisplayCategoryCommandService;
import com.aas.display.application.queryservice.DisplayCategoryQueryService;
import com.aas.display.application.queryservice.query.GetDisplayCategoryQuery;
import com.aas.display.application.queryservice.query.response.DisplayCategoryQueryResponse;
import com.aas.display.application.commandservice.command.*;
import com.aas.display.interfaces.dto.req.*;
import com.aas.display.interfaces.dto.rsp.*;
import com.aas.display.interfaces.controller.transfer.DisplayCategoryTransfer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Display Category Management", description = "전시 카테고리 계층 구조 및 상품 매핑 관리 API")
@RestController
@RequestMapping("/api/v1/display/displayCategoryMgmt")
@RequiredArgsConstructor
public class DisplayCategoryRestController {

    private final DisplayCategoryQueryService queryService;
    private final DisplayCategoryCommandService commandService;
    private final DisplayCategoryTransfer domainTransfer;

    @Operation(summary = "전시 카테고리 트리 조회", description = "사이트 및 전시몰별 카테고리 계층 구조를 트리 형태로 조회함.")
    @GetMapping("/getCategoryTreeList.do")
    public RspDto<List<DisplayCategoryRspDto>> getCategoryTreeList(DisplayCategoryReqDto dto) {
        GetDisplayCategoryQuery query = domainTransfer.toQuery(dto);
        List<DisplayCategoryQueryResponse> responses = queryService.getCategoryTreeList(query);
        return RspDto.ok(domainTransfer.toRspList(responses));
    }

    @Operation(summary = "전시 카테고리 상세 정보 조회", description = "특정 전시 카테고리의 상세 설정 정보를 조회함.")
    @GetMapping("/getCategoryDetail.do")
    public RspDto<DisplayCategoryRspDto> getCategoryDetail(DisplayCategoryReqDto dto) {
        GetDisplayCategoryQuery query = domainTransfer.toQuery(dto);
        DisplayCategoryQueryResponse response = queryService.getCategoryDetail(query);
        return RspDto.ok(domainTransfer.toRsp(response));
    }

    @Operation(summary = "하위 전시 카테고리 목록 조회", description = "부모 카테고리에 속한 하위 카테고리 목록을 조회함.")
    @GetMapping("/getSubCategoryList.do")
    public RspDto<List<DisplayCategoryRspDto>> getSubCategoryList(DisplayCategoryReqDto dto) {
        GetDisplayCategoryQuery query = domainTransfer.toQuery(dto);
        List<DisplayCategoryQueryResponse> responses = queryService.getSubCategoryList(query);
        return RspDto.ok(domainTransfer.toRspList(responses));
    }

    @Operation(summary = "전시 카테고리 목록(하위) 저장", description = "하위 카테고리의 추가, 수정, 삭제 처리를 한 번에 수행함.")
    @PostMapping("/saveCategoryList.do")
    public RspDto<Void> saveCategoryList(@RequestBody DisplayCategoryGridCudDto cudDto) {
        SaveDisplayCategoryGridCommand command = domainTransfer.toGridCommand(cudDto);
        commandService.saveCategoryList(command);
        return RspDto.ok(null);
    }

    @Operation(summary = "최상위 카테고리 추가", description = "전시몰 내에 새로운 최상위 카테고리를 생성함.")
    @PostMapping("/saveTopCategoryBasicInfo.do")
    public RspDto<String> saveTopCategoryBasicInfo(DisplayCategoryCudDto dto) {
        SaveDisplayCategoryCommand command = domainTransfer.toCommand(dto);
        String newNo = commandService.saveTopCategoryBasicInfo(command);
        return RspDto.ok(newNo);
    }

    @Operation(summary = "전시 상품 목록 조회", description = "해당 카테고리에 매핑된 전시 상품 목록을 조회함.")
    @GetMapping("/getDisplayGoodsList.do")
    public RspDto<List<DisplayCategoryRspDto.GoodsInfoRsp>> getDisplayGoodsList(DisplayCategoryReqDto dto) {
        GetDisplayCategoryQuery query = domainTransfer.toQuery(dto);
        int totalCount = queryService.getDisplayGoodsListCount(query);
        List<DisplayCategoryQueryResponse.GoodsInfoQueryResponse> responses = queryService.getDisplayGoodsList(query);

        return RspDto.ok(domainTransfer.toGoodsRspList(responses),
                MetaResponse.builder().total(totalCount).count(responses.size()).build());
    }

    @Operation(summary = "전시 상품 목록 저장", description = "카테고리별 상품 매핑 정보(추가, 수정, 삭제)를 저장함.")
    @PostMapping("/saveGoodsList.do")
    public RspDto<Void> saveGoodsList(@RequestBody DisplayCategoryGoodsGridCudDto cudDto) {
        SaveDisplayGoodsGridCommand command = domainTransfer.toGoodsGridCommand(cudDto);
        commandService.saveGoodsList(command);
        return RspDto.ok(null);
    }

    @Operation(summary = "상품 리스트 상세 조회", description = "대량의 상품 코드를 기반으로 상품 상세 속성을 일괄 조회함.")
    @GetMapping("/getGoodsListDetail.do")
    public RspDto<List<DisplayCategoryRspDto.GoodsInfoRsp>> getGoodsListDetail(DisplayCategoryReqDto dto) {
        GetDisplayCategoryQuery query = domainTransfer.toQuery(dto);
        List<DisplayCategoryQueryResponse.GoodsInfoQueryResponse> responses = queryService.getGoodsListDetail(query);
        return RspDto.ok(domainTransfer.toGoodsRspList(responses));
    }

    @Operation(summary = "상품 유효성 조회", description = "특정 상품이 해당 카테고리에 매핑 가능한 상태인지 확인함.")
    @GetMapping("/getCheckValidGoods.do")
    public RspDto<DisplayCategoryRspDto.GoodsInfoRsp> getCheckValidGoods(DisplayCategoryReqDto dto) {
        GetDisplayCategoryQuery query = domainTransfer.toQuery(dto);
        String validYn = queryService.getCheckValidGoods(query);
        DisplayCategoryRspDto.GoodsInfoRsp rsp = new DisplayCategoryRspDto.GoodsInfoRsp();
        rsp.setErrorMessage(validYn);
        return RspDto.ok(rsp);
    }

    /**
     * 다국어 정보 저장
     */
    @Operation(summary = "다국어 정보 저장", description = "하위 카테고리의 다국어 명칭 정보를 저장함.")
    @PostMapping("/saveSubCategoryMultiLanguage.do")
    public RspDto<Void> saveSubCategoryMultiLanguage(@RequestBody DisplayCategoryGridCudDto cudDto) {
        SaveDisplayCategoryGridCommand command = domainTransfer.toGridCommand(cudDto);
        commandService.saveSubCategoryMultiLanguage(command);
        return RspDto.ok(null);
    }
}
