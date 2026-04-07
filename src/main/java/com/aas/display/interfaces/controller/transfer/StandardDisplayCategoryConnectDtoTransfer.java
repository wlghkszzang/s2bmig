package com.aas.display.interfaces.controller.transfer;

import com.aas.display.interfaces.dto.req.StandardDisplayCategoryConnectCudDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StandardDisplayCategoryConnectDtoTransfer {
    // 특정 UI 맵핑이 필요한 경우 정의 (현재는 pass-through)
}
