package com.aas.goods.domain.repository.command;

import com.aas.goods.domain.model.PrOptnCd;
import com.aas.goods.domain.model.PrOptnClssCd;

public interface OptionMgmtCommandRepository {
    int insertPrOptnClssCd(PrOptnClssCd prOptnClssCd);
    int updatePrOptnClssCd(PrOptnClssCd prOptnClssCd);
    
    int insertPrOptnCd(PrOptnCd prOptnCd);
    int updatePrOptnCd(PrOptnCd prOptnCd);
}
