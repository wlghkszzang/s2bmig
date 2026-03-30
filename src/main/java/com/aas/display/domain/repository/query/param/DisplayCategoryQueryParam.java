package com.aas.display.domain.repository.query.param;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class DisplayCategoryQueryParam {
    private String dispCtgNo;
    private String siteNo;
    private String dpmlNo;
    private String useYn;
    private String langCd;
    private String dispCtgNm;
    
    // Goods Search
    private String dispYn;
    private String saleStatCd;
    private List<String> goodsArray;
    private String goodsNo;

    // Pagination
    private int rowsPerPage;
    private int pageIdx;
}
