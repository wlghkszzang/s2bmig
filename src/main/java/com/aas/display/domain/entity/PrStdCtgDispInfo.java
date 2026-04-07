package com.aas.display.domain.entity;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * 표준분류-전시카테고리 연결정보 엔티티
 */
@Getter
@Setter
public class PrStdCtgDispInfo {
    private String stdCtgNo;
    private String dispCtgNo;
    private String repCtgYn;
    private String siteNo;
    private String dpmlNo;
    private String useYn;
    private String delYn;
    
    // 시스템 정보
    private String sysRegId;
    private LocalDateTime sysRegDtm;
    private String sysModId;
    private LocalDateTime sysModDtm;
    
    // 조인 필드 (필요시)
    private String siteNm;
    private String dpmlNm;
    private String dispCtgNm;
}
