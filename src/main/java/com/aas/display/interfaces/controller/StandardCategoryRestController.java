package com.aas.display.interfaces.controller;

import com.aas.common.utils.RspDto;
import com.aas.common.utils.RspDto.MetaResponse;
import com.aas.display.application.queryservice.StandardCategoryQueryService;
import com.aas.display.application.commandservice.StandardCategoryCommandService;
import com.aas.display.interfaces.controller.transfer.StandardCategoryCommandDtoTransfer;
import com.aas.display.interfaces.dto.req.*;
import com.aas.display.interfaces.dto.rsp.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "Standard Category Management", description = "표준 카테고리 계층 및 속성 관리 API")
@RestController
@RequestMapping("/api/v1/display/standardCategoryMgmt")
@RequiredArgsConstructor
public class StandardCategoryRestController {

    private final StandardCategoryQueryService queryService;
    private final StandardCategoryCommandService commandService;
    private final StandardCategoryCommandDtoTransfer transfer;

    @Operation(summary = "표준 카테고리 트리 조회", description = "표준 카테고리의 전체 계층 구조를 트리 형태로 조회함.")
    @GetMapping("/getStandardCategoryMgmt.do")
    public RspDto<List<StandardCategoryMgmtRspDto>> getStandardCategoryMgmt() {
        return RspDto.ok(transfer.toRspDtoList(queryService.getStandardCategoryTree()));
    }

    @Operation(summary = "표준 카테고리 상세 정보 조회", description = "특정 표준 카테고리의 구성 정보를 조회함.")
    @GetMapping("/getStandardCategoryMgmtInfo.do")
    public RspDto<StandardCategoryMgmtRspDto> getStandardCategoryMgmtInfo(StandardCategoryReqDto request) {
        return RspDto.ok(transfer.toRspDto(queryService.getStandardCategoryInfo(transfer.toQuery(request))));
    }

    @Operation(summary = "하위 표준 카테고리 목록 조회", description = "부모 카테고리에 속한 하위 표준 카테고리 목록을 조회함.")
    @GetMapping("/getStandardCategoryMgmtChildList.do")
    public RspDto<List<StandardCategoryMgmtRspDto>> getStandardCategoryMgmtChildList(StandardCategoryReqDto request) {
        int totalCount = queryService.getChildListCount(transfer.toQuery(request));
        List<StandardCategoryMgmtRspDto> list = transfer.toRspDtoList(queryService.getChildList(transfer.toQuery(request)));
        return RspDto.ok(list, MetaResponse.builder().total(totalCount).count(list.size()).build());
    }

    @Operation(summary = "표준 카테고리 상품 목록 조회", description = "해당 표준 카테고리에 할당된 상품 목록을 조회함.")
    @GetMapping("/getStandardCategoryMgmtGoodsList.do")
    public RspDto<List<StandardCategoryMgmtRspDto.GoodsInfoRsp>> getStandardCategoryMgmtGoodsList(StandardCategoryReqDto request) {
        if (request.getLangCd() == null || request.getLangCd().isEmpty()) {
            request.setLangCd("ko");
        }
        int totalCount = queryService.getGoodsListCount(transfer.toQuery(request));
        List<StandardCategoryMgmtRspDto.GoodsInfoRsp> list = transfer.toGoodsRspList(queryService.getGoodsList(transfer.toQuery(request)));
        return RspDto.ok(list, MetaResponse.builder().total(totalCount).count(list.size()).build());
    }

    @Operation(summary = "표준 카테고리 속성 목록 조회", description = "표준 카테고리별 매핑된 상품 속성 정보를 조회함.")
    @GetMapping("/getStandardCategoryGoodsAttrList.do")
    public RspDto<List<StandardCategoryMgmtRspDto.AttrInfoRsp>> getStandardCategoryGoodsAttrList(StandardCategoryReqDto request) {
        if (request.getLangCd() == null || request.getLangCd().isEmpty()) {
            request.setLangCd("ko");
        }
        int totalCount = queryService.getAttrListCount(transfer.toQuery(request));
        List<StandardCategoryMgmtRspDto.AttrInfoRsp> list = transfer.toAttrRspList(queryService.getAttrList(transfer.toQuery(request)));
        return RspDto.ok(list, MetaResponse.builder().total(totalCount).count(list.size()).build());
    }

    @Operation(summary = "속성 코드 목록 조회", description = "공통 속성 마스터 정보를 조회함.")
    @GetMapping("/getAttCdInfo.do")
    public RspDto<List<StandardCategoryMgmtRspDto.AttrInfoRsp>> getAttInfoList(@RequestParam Map<String, Object> param) {
        if (param.get("langCd") == null) {
            param.put("langCd", "ko");
        }
        List<StandardCategoryMgmtRspDto.AttrInfoRsp> list = transfer.toAttrRspList(queryService.getAttInfoList(param));
        return RspDto.ok(list, MetaResponse.builder().total(list.size()).count(list.size()).build());
    }

    @Operation(summary = "표준 카테고리 정보 저장", description = "표준 카테고리의 본체 정보를 생성 또는 수정함.")
    @PostMapping("/saveStandardCategoryMgmtInfo.do")
    public RspDto<String> saveStandardCategoryMgmtInfo(@RequestBody StandardCategoryCudDto request) {
        commandService.saveStandardCategoryInfo(transfer.toCommand(request));
        return RspDto.ok("저장되었습니다.");
    }

    @Operation(summary = "하위 표준 카테고리 목록 저장", description = "그리드를 통해 편집된 하위 카테고리 목록(CUD)을 일괄 저장함.")
    @PostMapping("/saveStandardCategoryMgmtChildList.do")
    public RspDto<String> saveStandardCategoryMgmtChildList(@RequestBody StandardCategoryGridCudDto request) {
        commandService.saveStandardCategoryGrid(transfer.toGridCommand(request));
        return RspDto.ok("그리드 데이터가 저장되었습니다.");
    }

    @Operation(summary = "표준 카테고리 속성 매핑 저장", description = "카테고리에 매핑된 속성 편집 정보를 일괄 저장함.")
    @PostMapping("/saveStandardCategoryGoodsAttrList.do")
    public RspDto<String> saveStandardCategoryGoodsAttrList(@RequestBody StandardCategoryAttrGridCudDto request) {
        commandService.saveStandardCategoryAttrGrid(transfer.toAttrGridCommand(request));
        return RspDto.ok("속성 데이터가 저장되었습니다.");
    }
}
