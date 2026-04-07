package com.aas.display.interfaces.controller.transfer;

import com.aas.display.application.queryservice.query.GetDisplayCategoryQuery;
import com.aas.display.application.queryservice.query.response.DisplayCategoryQueryResponse;
import com.aas.display.application.commandservice.command.*;
import com.aas.display.interfaces.dto.req.*;
import com.aas.display.interfaces.dto.rsp.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DisplayCategoryTransfer {

    // DTO -> Query
    GetDisplayCategoryQuery toQuery(DisplayCategoryReqDto dto);

    // DTO -> Command
    SaveDisplayCategoryCommand toCommand(DisplayCategoryCudDto dto);
    List<SaveDisplayCategoryCommand> toCommandList(List<DisplayCategoryCudDto> dtos);

    // Grid CUD DTO -> Grid Command
    default SaveDisplayCategoryGridCommand toGridCommand(DisplayCategoryGridCudDto gridDto) {
        return SaveDisplayCategoryGridCommand.builder()
                .create(toCommandList(gridDto.getCreate()))
                .update(toCommandList(gridDto.getUpdate()))
                .delete(toCommandList(gridDto.getDelete()))
                .build();
    }

    // App Response -> RspDto
    DisplayCategoryRspDto toRsp(DisplayCategoryQueryResponse response);
    List<DisplayCategoryRspDto> toRspList(List<DisplayCategoryQueryResponse> responses);

    DisplayCategoryRspDto.GoodsInfoRsp toGoodsRsp(DisplayCategoryQueryResponse.GoodsInfoQueryResponse response);
    List<DisplayCategoryRspDto.GoodsInfoRsp> toGoodsRspList(List<DisplayCategoryQueryResponse.GoodsInfoQueryResponse> responses);

    // [전시 상품] CUD DTO -> Grid Command
    SaveDisplayGoodsGridCommand.SaveDisplayGoodsCommand toGoodsCommand(DisplayCategoryGoodsCudDto dto);
    List<SaveDisplayGoodsGridCommand.SaveDisplayGoodsCommand> toGoodsCommandList(List<DisplayCategoryGoodsCudDto> dtos);

    default SaveDisplayGoodsGridCommand toGoodsGridCommand(DisplayCategoryGoodsGridCudDto gridDto) {
        return SaveDisplayGoodsGridCommand.builder()
                .create(toGoodsCommandList(gridDto.getCreate()))
                .update(toGoodsCommandList(gridDto.getUpdate()))
                .delete(toGoodsCommandList(gridDto.getDelete()))
                .build();
    }
}
