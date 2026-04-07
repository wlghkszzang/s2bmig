package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 전시 카테고리 CUD DTO
 */
@Getter
@Setter
public class DisplayCategoryCudDto {
    private String dispCtgNo;
    private String siteNo;
    private String dpmlNo;
    private Integer dispSeq;
    private String useYn;
    private String uprDispCtgNo;
    private String lrgCtgNo;
    private String midCtgNo;
    private String smlCtgNo;
    private String thnCtgNo;
    private String leafYn;
    private String yes24CtgCd;
    private String dispCtgNm;
    private String langCd;
    
    private String sysRegId;
    private String sysModId;
}
