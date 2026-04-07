package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardDisplayCategoryConnectCudDto {
    private String stdCtgNo;
    private String dispCtgNo;
    private String repCtgYn;
    private String siteNo;
    private String dpmlNo;
    private String useYn;
    private String delYn;
    
    // 시스템 필드
    private String sysRegId;
    private String sysModId;

    // UI 호환 필드
    private String dispYn;
}