package com.aas.display.domain.repository.query.result;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StandardCategoryMgmtQueryResult {
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
}
