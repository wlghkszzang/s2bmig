package com.aas.goods.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;

/**
 * Application Layer: 옵션 코드 저장을 위한 단건 Command 데이터
 */
@Getter
@Setter
public class SaveOptionCodeCommandData {
    private String optnCatNo;
    private String optnNo;
    private Integer sortSeq;
    private String useYn;
    private String imgPathNm;
    private String imgFileNm;
    private String rgbVal;
    private String optnNm;
    
    private String sysRegId;
    private String sysModId;
}
