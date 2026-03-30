package com.aas.goods.domain.repository.query.param;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain Layer: Repository 조회를 위한 순수 파라미터 캡슐
 */
@Getter
@Setter
public class OptionMgmtQueryParam {
    private String condxOptnCatTypCd;
    private String condxOptnCatRegGbCd;
    private String condxUseYn;
    private String optnCatNo;
    private String entrNo;
    private String langCd;
    private String useYn;
    private String sysGbCd;
}
