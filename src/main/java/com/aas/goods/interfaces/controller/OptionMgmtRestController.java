package com.aas.goods.interfaces.controller;

import org.springframework.web.bind.annotation.*;
import com.aas.common.utils.RspDto;
import com.aas.goods.interfaces.controller.dto.OptionMgmtReqDto;
import com.aas.goods.interfaces.controller.dto.OptionMgmtRspDto;
import com.aas.goods.application.queryservice.OptionMgmtQueryService;
import com.aas.goods.application.queryservice.query.GetOptionListQuery;
import com.aas.goods.application.queryservice.query.OptionListQueryResponse;
import com.aas.goods.application.commandservice.OptionMgmtCommandService;
import com.aas.goods.interfaces.controller.transfer.OptionMgmtCommandDtoTransfer;
import com.aas.goods.application.commandservice.command.SaveOptionCategoryListCommand;
import com.aas.goods.application.commandservice.command.SaveOptionCodeListCommand;
import com.aas.common.dto.GridCudReqDto;
import com.aas.goods.interfaces.controller.dto.OptionMgmtCategoryCudDto;
import com.aas.goods.interfaces.controller.dto.OptionMgmtCodeCudDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Tag(name = "Option Management", description = "상품 옵션 분류 및 코드 관리 API")
@RestController
@RequestMapping("/api/v1/goods/optionMgmt")
@RequiredArgsConstructor
public class OptionMgmtRestController {

    private final OptionMgmtQueryService optionMgmtQueryService;
    private final OptionMgmtCommandService optionMgmtCommandService;
    private final OptionMgmtCommandDtoTransfer transfer;

    @Operation(summary = "옵션 분류 목록 조회", description = "전체 옵션 카테고리(분류) 목록을 조회함.")
    @GetMapping("/getOptionCategoryList.do")
    public RspDto<List<OptionMgmtRspDto>> getOptionCategoryList(OptionMgmtReqDto request) {
        GetOptionListQuery query = transfer.toQuery(request);
        List<OptionListQueryResponse> responseList = optionMgmtQueryService.getOptionCategoryList(query);
        return RspDto.ok(transfer.toRspDtoList(responseList));
    }

    @Operation(summary = "옵션 코드 목록 조회", description = "특정 분류에 속한 세부 옵션 코드 목록을 조회함.")
    @GetMapping("/getOptionList.do")
    public RspDto<List<OptionMgmtRspDto>> getOptionList(OptionMgmtReqDto request) {
        GetOptionListQuery query = transfer.toQuery(request);
        List<OptionListQueryResponse> responseList = optionMgmtQueryService.getOptionList(query);
        return RspDto.ok(transfer.toRspDtoList(responseList));
    }

    @Operation(summary = "옵션 분류 저장", description = "그리드에서 추가/수정/삭제된 옵션 분류 정보를 일괄 저장함.")
    @PostMapping("/saveOptionCategoryList.do")
    public RspDto<String> saveOptionCategoryList(@RequestBody GridCudReqDto<OptionMgmtCategoryCudDto> request) {
        SaveOptionCategoryListCommand command = transfer.toCategoryListCommand(request);
        optionMgmtCommandService.saveOptionCategoryList(command);
        return RspDto.ok("SUCCESS");
    }

    @Operation(summary = "옵션 코드 저장", description = "그리드에서 추가/수정/삭제된 옵션 코드 정보를 일괄 저장함.")
    @PostMapping("/saveOptionList.do")
    public RspDto<String> saveOptionList(@RequestBody GridCudReqDto<OptionMgmtCodeCudDto> request) {
        SaveOptionCodeListCommand command = transfer.toCodeListCommand(request);
        optionMgmtCommandService.saveOptionList(command);
        return RspDto.ok("SUCCESS");
    }
}
