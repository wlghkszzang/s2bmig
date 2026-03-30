package com.aas.display.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardCategoryCudDto {
    private String stdCtgNo;
    private String stdCtgNm;
    private String leafCtgYn;
    private String useYn;
    private Integer sortSeq;
    private String uprStdCtgNo;
    private String safeCertiNeedYn;
    private String goodsNotiLisartCd;
    private String hsCd;
    private Double mrgnRate;
    private String samMarketYn;
    private String mdId;
}
