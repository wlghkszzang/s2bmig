package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 전시 카테고리 상품 CUD DTO
 */
@Getter
@Setter
public class DisplayCategoryGoodsCudDto {
    private String goodsNo;
    private String dispCtgNo;
    private String dispYn;
    private List<String> goodsList; // bulk delete용
    private String langCd;

    private String sysRegId;
    private String sysModId;
}
