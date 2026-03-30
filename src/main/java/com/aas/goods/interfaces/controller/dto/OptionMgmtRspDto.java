package com.aas.goods.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionMgmtRspDto {
    private String optnCatNo;
    private String optnCatNm;
    private String optnCatRegGbCd;
    private String optnCatTypCd;
    private String entrNo;
    private String entrNm;
    private String useYn;
    private Integer sortSeq;
    private Integer totalCount;

    // 추가 옵션 상세 코드 관련 정보
    private String optnNo;
    private String optnNm;
    private String imgPathNm;
    private String imgFileNm;
    private String rgbVal;
    private String langCd;
    private String optnCatRegGbCdNm;
    private String optnCatTypCdNm;
    private String sysRegId;
    private String sysRegDtm;
    private String sysModId;
    private String sysModDtm;
}
