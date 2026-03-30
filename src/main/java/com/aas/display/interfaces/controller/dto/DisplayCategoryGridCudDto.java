package com.aas.display.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * 전시 카테고리 그리드 CUD DTO (Bulk)
 */
@Getter
@Setter
public class DisplayCategoryGridCudDto {
    private List<DisplayCategoryCudDto> create;
    private List<DisplayCategoryCudDto> update;
    private List<DisplayCategoryCudDto> delete;
}
