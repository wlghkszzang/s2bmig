package com.aas.display.interfaces.dto.rsp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardDisplayCategoryConnectRspDto {
    private String stdCtgNo;
    private String dispCtgNm;
    private String dispCtgNo;
    private String repCtgYn;
    private String dispYn; // AS-IS: USE_YN
    private String siteNo;
    private String siteNm;
    private String dpmlNo;
    private String dpmlNm;
    private String sysModId;
    private String sysModDtm;
}
