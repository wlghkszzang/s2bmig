package com.aas.display.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveStandardCategoryAttrCommand {
    private String stdCtgNo;
    private String attCd;
    private Integer dispSeq;
    private String useYn;
}
