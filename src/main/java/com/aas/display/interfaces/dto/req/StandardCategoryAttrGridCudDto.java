package com.aas.display.interfaces.dto.req;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardCategoryAttrGridCudDto {
    private List<StandardCategoryAttrCommandDto> create;
    private List<StandardCategoryAttrCommandDto> update;
    private List<StandardCategoryAttrCommandDto> delete;

    @Getter
    @Setter
    public static class StandardCategoryAttrCommandDto {
        private String stdCtgNo;
        private String attCd;
        private int dispSeq;
        private String useYn;
    }
}
