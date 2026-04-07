package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 전시 카테고리 상품 그리드 CUD (Bulk) DTO
 */
@Getter
@Setter
public class DisplayCategoryGoodsGridCudDto {
    private List<DisplayCategoryGoodsCudDto> create;
    private List<DisplayCategoryGoodsCudDto> update;
    private List<DisplayCategoryGoodsCudDto> delete;
}
