package com.aas.goods.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Interface DTO: 옵션 분류 CUD
 */
@Getter
@Setter
public class OptionMgmtCategoryCudDto {
    private String optnCatNo;
    private String optnCatRegGbCd;
    private String optnCatTypCd;
    private Integer sortSeq;
    private String useYn;
    private String entrNo;
    private String optnCatNm;
    
    private String sysRegId = "test123";
    private String sysModId = "test123";
}
