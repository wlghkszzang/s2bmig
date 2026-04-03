package com.aas.display.domain.repository;

import java.util.List;
import com.aas.display.domain.model.StandardDisplayCategoryConnectQueryParam;
import com.aas.display.interfaces.dto.rsp.StandardDisplayCategoryConnectRspDto;

public interface StandardDisplayCategoryConnectQueryRepository {
    List<StandardDisplayCategoryConnectRspDto> selectConnectList(StandardDisplayCategoryConnectQueryParam param);
}
