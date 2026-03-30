package com.aas.display.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 전시 카테고리 요청 DTO
 */
@Getter
@Setter
public class DisplayCategoryReqDto {
    private String dispCtgNo;
    private String siteNo;
    private String dpmlNo;
    private String useYn;
    private String langCd = "ko";
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
