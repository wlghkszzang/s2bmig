package com.aas.display.interfaces.dto.rsp;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StandardCategoryMgmtRspDto {
    private String stdCtgNo;
    private String stdCtgNm;
    private String leafCtgYn;
    private String useYn;
    private Integer sortSeq;
    private String uprStdCtgNo;
    private String uprStdCtgNm;
    private String stdLrgCtgNo;
    private String stdMidCtgNo;
    private String stdSmlCtgNo;
    private String stdThnCtgNo;
    private String mdId;
    private String mdNm;
    private String safeCertiNeedYn;
    private String goodsNotiLisartCd;
    private String goodsNotiLisartCdNm;
    private String hsCd;
    private Double mrgnRate;
    private String samMarketYn;
    private String hierarchyText;
    private String leafCtgYnChangePossible;
    
    private String sysRegId;
    private LocalDateTime sysRegDtm;
    private String sysModId;
    private LocalDateTime sysModDtm;

    @Getter
    @Setter
    public static class GoodsInfoRsp {
        private String goodsNo;
        private String goodsNm;
        private String saleStatCdNm;
    }

    @Getter
    @Setter
    public static class AttrInfoRsp {
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
