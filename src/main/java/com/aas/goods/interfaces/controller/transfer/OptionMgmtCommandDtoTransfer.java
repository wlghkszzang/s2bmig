package com.aas.goods.interfaces.controller.transfer;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.aas.goods.application.commandservice.command.SaveOptionCategoryCommandData;
import com.aas.goods.application.commandservice.command.SaveOptionCategoryListCommand;
import com.aas.goods.application.commandservice.command.SaveOptionCodeCommandData;
import com.aas.goods.application.commandservice.command.SaveOptionCodeListCommand;
import com.aas.goods.application.queryservice.query.OptionListQueryResponse;
import com.aas.goods.interfaces.controller.dto.OptionMgmtCategoryCudDto;
import com.aas.goods.interfaces.controller.dto.OptionMgmtCodeCudDto;
import com.aas.common.dto.GridCudReqDto;

/**
 * MapStruct DTO <-> Command 변환 (Interface 패키지에 위치)
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OptionMgmtCommandDtoTransfer {

    // [옵션 분류] DTO -> Command Data
    SaveOptionCategoryCommandData toCategoryCommandData(OptionMgmtCategoryCudDto dto);
    List<SaveOptionCategoryCommandData> toCategoryCommandDataList(List<OptionMgmtCategoryCudDto> dtoList);

    // [옵션 분류] Grid Request -> List Command
    default SaveOptionCategoryListCommand toCategoryListCommand(GridCudReqDto<OptionMgmtCategoryCudDto> req) {
        SaveOptionCategoryListCommand cmd = new SaveOptionCategoryListCommand();
        cmd.setCreateList(toCategoryCommandDataList(req.getCreate()));
        cmd.setUpdateList(toCategoryCommandDataList(req.getUpdate()));
        return cmd;
    }

    // [옵션 상세코드] DTO -> Command Data
    SaveOptionCodeCommandData toCodeCommandData(OptionMgmtCodeCudDto dto);
    List<SaveOptionCodeCommandData> toCodeCommandDataList(List<OptionMgmtCodeCudDto> dtoList);
    
    // [옵션 상세코드] Grid Request -> List Command
    default SaveOptionCodeListCommand toCodeListCommand(GridCudReqDto<OptionMgmtCodeCudDto> req) {
        SaveOptionCodeListCommand cmd = new SaveOptionCodeListCommand();
        cmd.setCreateList(toCodeCommandDataList(req.getCreate()));
        cmd.setUpdateList(toCodeCommandDataList(req.getUpdate()));
        return cmd;
    }

    // [공통] App Response -> Response DTO (역방향)
    com.aas.goods.interfaces.controller.dto.OptionMgmtRspDto toRspDto(OptionListQueryResponse response);
    List<com.aas.goods.interfaces.controller.dto.OptionMgmtRspDto> toRspDtoList(List<OptionListQueryResponse> responseList);

    // --- Query 계층 ---
    // ReqDto -> App Query 캡슐 변환
    com.aas.goods.application.queryservice.query.GetOptionListQuery toQuery(com.aas.goods.interfaces.controller.dto.OptionMgmtReqDto dto);
}
