package com.aas.display.interfaces.controller.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class StandardCategoryAttrGridCudDto {
    private List<StandardCategoryAttrCudDto> create;
    private List<StandardCategoryAttrCudDto> update;
    private List<StandardCategoryAttrCudDto> delete;
}
