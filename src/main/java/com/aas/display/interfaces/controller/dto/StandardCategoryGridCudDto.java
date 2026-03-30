package com.aas.display.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class StandardCategoryGridCudDto {
    private List<StandardCategoryCudDto> create;
    private List<StandardCategoryCudDto> update;
    private List<StandardCategoryCudDto> delete;
}
