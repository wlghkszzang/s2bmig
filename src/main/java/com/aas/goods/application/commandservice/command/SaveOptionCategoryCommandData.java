package com.aas.goods.application.commandservice.command;

import lombok.Getter;
import lombok.Setter;

/**
 * Application Layer: 옵션 분류 저장을 위한 단건 Command 데이터
 */
@Getter
@Setter
public class SaveOptionCategoryCommandData {
    private String optnCatNo;
    private String optnCatRegGbCd;
    private String optnCatTypCd;
    private Integer sortSeq;
    private String useYn;
    private String entrNo;
    private String optnCatNm;

    private String sysRegId;
    private String sysModId;
}
