package com.aas.goods.infrastructure.db;

import com.aas.goods.domain.repository.query.param.OptionMgmtQueryParam;
import com.aas.goods.domain.repository.query.result.OptionMgmtQueryResult;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Infrastructure Layer: DB 접근을 담당하는 실제 MyBatis Mapper 인터페이스
 */
@Mapper
public interface OptionMgmtQueryMyBatisDao {
    List<OptionMgmtQueryResult> getOptionCategoryList(OptionMgmtQueryParam request);
    List<OptionMgmtQueryResult> getOptionList(OptionMgmtQueryParam request);
}
