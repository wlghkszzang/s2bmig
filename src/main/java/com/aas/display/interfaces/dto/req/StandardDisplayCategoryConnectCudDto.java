package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardDisplayCategoryConnectCudDto {
    private String stdCtgNo;
    private String dispCtgNo;
    private String dispCtgNm;
    private String repCtgYn;
    private String useYn;
    private String siteNo;
    private String siteNoNm; // Optional
    private String dpmlNo;
    private String dpmlNm;
    
    // Audit fields for convenience if needed, though they could be set in Service
    private String sysRegId;
    private String sysModId;
}
