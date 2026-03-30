package com.aas.goods.application.queryservice.query;

import lombok.Getter;
import lombok.Setter;

/**
 * Application Layer: Service 계층 수신용 조회 캡슐 객체
 */
@Getter
@Setter
public class GetOptionListQuery {
    private String condxOptnCatTypCd;
    private String condxUseYn;
    private String condxOptnCatRegGbCd;
    private String optnCatNo;
    private String entrNo;
    private String useYn;
    private String sysGbCd;
}
