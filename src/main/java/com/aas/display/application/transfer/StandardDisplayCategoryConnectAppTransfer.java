package com.aas.display.application.transfer;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
import com.aas.display.interfaces.dto.rsp.StandardDisplayCategoryConnectRspDto;
import com.aas.display.domain.entity.PrStdCtgDispInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardDisplayCategoryConnectAppTransfer {
    
    // Entity -> Response DTO
    @Mapping(target = "useYn", source = "useYn") // UI에서 useYn으로 받음
    StandardDisplayCategoryConnectRspDto toRspDto(PrStdCtgDispInfo entity);
    List<StandardDisplayCategoryConnectRspDto> toRspDtoList(List<PrStdCtgDispInfo> entities);

    // CUD DTO -> Entity
    @Mapping(target = "useYn", source = "useYn") // UI에서 useYn으로 전송됨
    PrStdCtgDispInfo toEntity(StandardDisplayCategoryConnectCudDto dto);
    List<PrStdCtgDispInfo> toEntityList(List<StandardDisplayCategoryConnectCudDto> dtos);
}
