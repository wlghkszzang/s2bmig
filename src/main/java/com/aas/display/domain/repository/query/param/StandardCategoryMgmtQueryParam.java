package com.aas.display.domain.repository.query.param;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardCategoryMgmtQueryParam {
    private String stdCtgNo;
    private String langCd;
    private String useYn;
    private Double rangeFrom;
    private Double rangeTo;
    
    // Pagination
    private int rowsPerPage = 100;
    private int pageIdx = 1;

    public void setLangCd(String langCd) {
        if (langCd == null || langCd.isEmpty()) {
            this.langCd = "ko";
        } else {
            this.langCd = langCd;
        }
    }
}
