package com.aas.display.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 전시 카테고리 응답 DTO
 */
@Getter
@Setter
public class DisplayCategoryRspDto {
    private String dispCtgNo;
    private String siteNo;
    private String siteNm;
    private String dpmlNo;
    private String dpmlNm;
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
    private String multiDispCtgNm;

    private String lrgCtgNm;
    private String midCtgNm;
    private String smlCtgNm;
    private String thnCtgNm;
    private String hierarchyText;
    private String catGoodsCnt;
    private String leafCtgYnChangePossible;
    private String shopNo;
    private String prtTypCd;

    private String sysRegId;
    private LocalDateTime sysRegDtm;
    private String sysModId;
    private LocalDateTime sysModDtm;

    @Getter
    @Setter
    public static class GoodsInfoRsp {
        private String goodsNo;
        private String dispCtgNo;
        private String goodsNm;
        private String brandNo;
        private String dispYn;
        private String goodsTypCd;
        private Integer norPrc;
        private Integer salePrc;
        private String saleStatCd;
        private String entrNm;
        private String sysRegId;
        private LocalDateTime sysRegDtm;
        private String sysModId;
        private LocalDateTime sysModDtm;
        private String errorMessage;
    }
}
