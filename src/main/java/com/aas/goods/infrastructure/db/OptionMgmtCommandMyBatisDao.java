package com.aas.goods.infrastructure.db;

import com.aas.goods.domain.model.PrOptnCd;
import com.aas.goods.domain.model.PrOptnClssCd;
import org.apache.ibatis.annotations.Mapper;

/**
 * Infrastructure Layer: DB 접근을 담당하는 실제 MyBatis Mapper 인터페이스
 */
@Mapper
public interface OptionMgmtCommandMyBatisDao {
    int insertPrOptnClssCd(PrOptnClssCd prOptnClssCd);
    int updatePrOptnClssCd(PrOptnClssCd prOptnClssCd);

    int insertPrOptnCd(PrOptnCd prOptnCd);
    int updatePrOptnCd(PrOptnCd prOptnCd);
}
