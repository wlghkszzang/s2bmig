package com.aas.display.application.queryservice.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetStandardCategoryQuery {
    private String stdCtgNo;
    private String langCd;
    private String useYn;
    private Double rangeFrom;
    private Double rangeTo;
    
    // Pagination
    private int rowsPerPage = 100;
    private int pageIdx = 1;
}
