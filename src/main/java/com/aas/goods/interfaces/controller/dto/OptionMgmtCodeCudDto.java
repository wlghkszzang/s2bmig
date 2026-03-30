package com.aas.goods.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Interface DTO: 옵션 코드 CUD
 */
@Getter
@Setter
public class OptionMgmtCodeCudDto {
    private String optnCatNo;
    private String optnNo;
    private Integer sortSeq;
    private String useYn;
    private String imgPathNm;
    private String imgFileNm;
    private String rgbVal;
    private String optnNm;
    
    private String sysRegId = "test123";
    private String sysModId = "test123";
}
