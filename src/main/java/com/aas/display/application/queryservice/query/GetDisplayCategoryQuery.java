package com.aas.display.application.queryservice.query;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class GetDisplayCategoryQuery {
    private String dispCtgNo;
    private String siteNo;
    private String dpmlNo;
    private String useYn;
    private String langCd;
    private String dispCtgNm;
    
    // Goods List Search
    private String dispYn;
    private String saleStatCd;
    private List<String> goodsArray;
    private String goodsNo;

    // Pagination
    private int rowsPerPage = 100;
    private int pageIdx = 1;
}
