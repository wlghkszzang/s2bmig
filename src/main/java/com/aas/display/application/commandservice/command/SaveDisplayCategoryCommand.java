package com.aas.display.application.commandservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDisplayCategoryCommand {
    private String dispCtgNo;
    private String uprDispCtgNo;
    private String dispCtgNm;
    private String siteNo;
    private String dpmlNo;
    private String leafYn;
    private Integer dispSeq;
    private String useYn;
    private String yes24CtgCd;
}
