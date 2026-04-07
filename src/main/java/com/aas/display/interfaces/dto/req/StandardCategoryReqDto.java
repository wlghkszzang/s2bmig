package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardCategoryReqDto {
    private String stdCtgNo;
    private String langCd;
    private String useYn;
    private Double rangeFrom;
    private Double rangeTo;
    
    // Pagination
    private int rowsPerPage = 100;
    private int pageIdx = 1;
}
