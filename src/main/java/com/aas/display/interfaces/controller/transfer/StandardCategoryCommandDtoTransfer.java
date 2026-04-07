package com.aas.display.interfaces.controller.transfer;

import com.aas.display.application.commandservice.command.SaveStandardCategoryCommand;
import com.aas.display.application.commandservice.command.SaveStandardCategoryGridCommand;
import com.aas.display.application.commandservice.command.SaveStandardCategoryAttrGridCommand;
import com.aas.display.application.queryservice.query.GetStandardCategoryQuery;
import com.aas.display.application.queryservice.query.response.StandardCategoryAttrQueryResponse;
import com.aas.display.application.queryservice.query.response.StandardCategoryGoodsQueryResponse;
import com.aas.display.application.queryservice.query.response.StandardCategoryQueryResponse;
import com.aas.display.interfaces.dto.req.*;
import com.aas.display.interfaces.dto.rsp.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardCategoryCommandDtoTransfer {

    // Request DTO -> Query
    GetStandardCategoryQuery toQuery(StandardCategoryReqDto dto);

    // Request DTO (CUD) -> Command
    SaveStandardCategoryCommand toCommand(StandardCategoryCudDto dto);
    
    // Grid (Bulky) CUD
    SaveStandardCategoryGridCommand toGridCommand(StandardCategoryGridCudDto dto);
    SaveStandardCategoryAttrGridCommand toAttrGridCommand(StandardCategoryAttrGridCudDto dto);

    // Application Response -> Interface Response DTO
    StandardCategoryMgmtRspDto toRspDto(StandardCategoryQueryResponse result);
    List<StandardCategoryMgmtRspDto> toRspDtoList(List<StandardCategoryQueryResponse> resultList);

    StandardCategoryMgmtRspDto.GoodsInfoRsp toGoodsRsp(StandardCategoryGoodsQueryResponse result);
    List<StandardCategoryMgmtRspDto.GoodsInfoRsp> toGoodsRspList(List<StandardCategoryGoodsQueryResponse> resultList);

    StandardCategoryMgmtRspDto.AttrInfoRsp toAttrRsp(StandardCategoryAttrQueryResponse result);
    List<StandardCategoryMgmtRspDto.AttrInfoRsp> toAttrRspList(List<StandardCategoryAttrQueryResponse> resultList);
}
