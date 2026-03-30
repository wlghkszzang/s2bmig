package com.aas.display.domain.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PrStdCtg {
    private String stdCtgNo;
    private String stdCtgNm;
    private String leafCtgYn;
    private String useYn;
    private Integer sortSeq;
    private String uprStdCtgNo;
    private String stdLrgCtgNo;
    private String stdMidCtgNo;
    private String stdSmlCtgNo;
    private String stdThnCtgNo;
    private String stdDtlsCtgNo;
    private String mdId;
    private String safeCertiNeedYn;
    private String goodsNotiLisartCd;
    private String hsCd;
    private Double mrgnRate;
    private String samMarketYn;

    // Join fields
    private String mdNm;
    private String goodsNotiLisartCdNm;
    private String leafCtgYnChangePossible;
    private String uprStdCtgNm;
    private String hierarchyText;

    private String sysRegId;
    private LocalDateTime sysRegDtm;
    private String sysModId;
    private LocalDateTime sysModDtm;

    @Getter
    @Setter
    public static class GoodsInfo {
        private String goodsNo;
        private String goodsNm;
        private String saleStatCdNm;
    }

    @Getter
    @Setter
    public static class AttrInfo {
        private String stdCtgNo;
        private String attCd;
        private String attNm;
        private String attValTypCd;
        private String attValTypNm;
        private Integer dispSeq;
        private String useYn;
        private String attCdExpYn;
        private String sysModId;
        private LocalDateTime sysModDtm;
    }
}
