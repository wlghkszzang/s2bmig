package com.aas.goods.domain.repository.query;

import java.util.List;
import java.util.List;
import com.aas.goods.domain.repository.query.param.OptionMgmtQueryParam;
import com.aas.goods.domain.repository.query.result.OptionMgmtQueryResult;

public interface OptionMgmtQueryRepository {
    List<OptionMgmtQueryResult> getOptionCategoryList(OptionMgmtQueryParam request);
    List<OptionMgmtQueryResult> getOptionList(OptionMgmtQueryParam request);
}
