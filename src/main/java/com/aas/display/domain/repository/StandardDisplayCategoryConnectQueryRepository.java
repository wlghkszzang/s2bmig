package com.aas.display.domain.repository;

import java.util.List;
import com.aas.display.domain.model.StandardDisplayCategoryConnectQueryParam;

public interface StandardDisplayCategoryConnectQueryRepository {
    List<Object> selectConnectList(StandardDisplayCategoryConnectQueryParam param);
}
