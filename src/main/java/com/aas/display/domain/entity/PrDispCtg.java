package com.aas.display.domain.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PrDispCtg {
    // PR_DISP_CTG_BASE
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

    // PR_DISP_CTG_BASE_ML (Join or Property)
    private String dispCtgNm;
    private String multiDispCtgNm;
    private String langCd;

    // Join Fields
    private String siteNm;
    private String dpmlNm;
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
    public static class GoodsInfo {
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
        
        // UI Helpers
        private String errorMessage;
        private List<String> goodsList; // bulk delete용
    }
}
