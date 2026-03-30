package com.aas.goods.infrastructure.db;

import com.aas.goods.domain.model.PrOptnCd;
import com.aas.goods.domain.model.PrOptnClssCd;
import com.aas.goods.domain.repository.command.OptionMgmtCommandRepository;
import com.aas.goods.infrastructure.db.OptionMgmtCommandMyBatisDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Infrastructure Layer: 도메인 인터페이스를 구현(Adapter)하여 실제 DB 접근 로직(DAO)을 캡슐화
 */
@Repository
@RequiredArgsConstructor
public class OptionMgmtCommandRepositoryImpl implements OptionMgmtCommandRepository {

    private final OptionMgmtCommandMyBatisDao myBatisDao;

    @Override
    public int insertPrOptnClssCd(PrOptnClssCd prOptnClssCd) {
        return myBatisDao.insertPrOptnClssCd(prOptnClssCd);
    }

    @Override
    public int updatePrOptnClssCd(PrOptnClssCd prOptnClssCd) {
        return myBatisDao.updatePrOptnClssCd(prOptnClssCd);
    }

    @Override
    public int insertPrOptnCd(PrOptnCd prOptnCd) {
        return myBatisDao.insertPrOptnCd(prOptnCd);
    }

    @Override
    public int updatePrOptnCd(PrOptnCd prOptnCd) {
        return myBatisDao.updatePrOptnCd(prOptnCd);
    }
}
