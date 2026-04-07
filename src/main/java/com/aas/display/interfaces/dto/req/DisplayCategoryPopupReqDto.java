package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 전시 카테고리 팝업 요청 DTO
 */
@Getter
@Setter
public class DisplayCategoryPopupReqDto {
    private String argSelectType;
    private String argUseYn;
    private String argSiteNo;
    private String argDpmlNo;
    
    // 비즈니스 로직 연동용 자동 매핑 필드
    private String siteNo;
    private String dpmlNo;
    private String useYn;
    private String dispCtgNm;

    /**
     * argXXX 필드를 일반 XXX 필드로 동기화 (레거시/신규 혼용 대응)
     */
    public void syncArgs() {
        if (this.argSiteNo != null && this.siteNo == null) this.siteNo = this.argSiteNo;
        if (this.argDpmlNo != null && this.dpmlNo == null) this.dpmlNo = this.argDpmlNo;
        if (this.argUseYn != null && this.useYn == null) this.useYn = this.argUseYn;
    }
}
