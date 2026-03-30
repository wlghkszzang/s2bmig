package com.aas.goods.infrastructure.db;

import com.aas.goods.domain.repository.query.OptionMgmtQueryRepository;
import com.aas.goods.infrastructure.db.OptionMgmtQueryMyBatisDao;
import com.aas.goods.domain.repository.query.param.OptionMgmtQueryParam;
import com.aas.goods.domain.repository.query.result.OptionMgmtQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Infrastructure Layer: 도메인 인터페이스를 구현(Adapter)하여 실제 DB 접근 로직(DAO)을 캡슐화
 */
@Repository
@RequiredArgsConstructor
public class OptionMgmtQueryRepositoryImpl implements OptionMgmtQueryRepository {

    private final OptionMgmtQueryMyBatisDao myBatisDao;

    @Override
    public List<OptionMgmtQueryResult> getOptionCategoryList(OptionMgmtQueryParam request) {
        return myBatisDao.getOptionCategoryList(request);
    }

    @Override
    public List<OptionMgmtQueryResult> getOptionList(OptionMgmtQueryParam request) {
        return myBatisDao.getOptionList(request);
    }
}
