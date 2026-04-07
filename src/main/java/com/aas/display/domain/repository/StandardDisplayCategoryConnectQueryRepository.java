package com.aas.display.domain.repository;

import com.aas.display.domain.model.StandardDisplayCategoryConnectQueryParam;
import com.aas.display.domain.entity.PrStdCtgDispInfo;
import java.util.List;

public interface StandardDisplayCategoryConnectQueryRepository {
    List<PrStdCtgDispInfo> selectConnectList(StandardDisplayCategoryConnectQueryParam param);
}