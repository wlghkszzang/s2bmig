package com.aas.display.interfaces.controller;

import com.aas.common.utils.RspDto;
import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectReqDto;
import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
import com.aas.display.interfaces.dto.rsp.StandardDisplayCategoryConnectRspDto;
import com.aas.display.application.queryservice.StandardDisplayCategoryConnectQueryService;
import com.aas.display.application.commandservice.StandardDisplayCategoryConnectCommandService;
import com.aas.display.interfaces.controller.transfer.StandardDisplayCategoryConnectDtoTransfer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 헥사고날 규칙 및 CQRS 준수
 * 표준분류 & 전시카테고리 연결 REST API 컨트롤러
 */
@Tag(name = "표준분류&전시카테고리연결 관리")
@RestController
@RequestMapping("/api/v1/display/standardDisplayCategoryConnect")
@Slf4j
@RequiredArgsConstructor
public class StandardDisplayCategoryConnectRestController {

    private final StandardDisplayCategoryConnectQueryService queryService;
    private final StandardDisplayCategoryConnectCommandService commandService;
    private final StandardDisplayCategoryConnectDtoTransfer dtoTransfer;

    @Operation(summary = "연결 소전시 카테고리 목록 조회", description = "AUIGrid 용 연결된 소전시 카테고리 목록을 조회합니다.")
    @GetMapping("/getStandardDisplayCategoryConnect.do")
    public RspDto<List<StandardDisplayCategoryConnectRspDto>> getStandardDisplayCategoryConnect(
            StandardDisplayCategoryConnectReqDto reqDto) {

        List<StandardDisplayCategoryConnectRspDto> result = queryService.getList(reqDto);
        return RspDto.ok(result);
    }

    @Operation(summary = "연결 소전시 카테고리 목록 저장 (CUD)", description = "AUIGrid에서 넘어온 추가/수정/삭제 목록을 한번에 저장합니다.")
    @PostMapping("/saveStandardDisplayCategoryConnect.do")
    public RspDto<String> saveStandardDisplayCategoryConnect(
            @RequestBody Map<String, List<StandardDisplayCategoryConnectCudDto>> realGridCUD) {

        List<StandardDisplayCategoryConnectCudDto> createList = realGridCUD.get("create");
        List<StandardDisplayCategoryConnectCudDto> updateList = realGridCUD.get("update");
        List<StandardDisplayCategoryConnectCudDto> deleteList = realGridCUD.get("delete");

        // [임시] 시스템 필드 강제 주입 (로그인 세션 적용 전까지 ADMIN 무결성 확보)
        if (createList != null)
            createList.forEach(dto -> {
                dto.setSysRegId("ADMIN");
                dto.setSysModId("ADMIN");
            });
        if (updateList != null)
            updateList.forEach(dto -> {
                dto.setSysModId("ADMIN");
            });
        if (deleteList != null)
            deleteList.forEach(dto -> {
                dto.setSysModId("ADMIN");
            });

        commandService.saveCategoryConnect(createList, updateList, deleteList);
        return RspDto.ok("저장되었습니다.");
    }
}
