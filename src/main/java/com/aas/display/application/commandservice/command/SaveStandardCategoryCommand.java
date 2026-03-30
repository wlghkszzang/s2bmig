package com.aas.display.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveStandardCategoryCommand {
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
