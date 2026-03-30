package com.aas.goods.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrOptnClssCd {
    private String optnCatNo;
    private String optnCatRegGbCd;
    private String optnCatTypCd;
    private Integer sortSeq;
    private String useYn;
    private String entrNo;
    private String sysRegId;
    private String sysModId;
    
    // from ml
    private String optnCatNm;
}
