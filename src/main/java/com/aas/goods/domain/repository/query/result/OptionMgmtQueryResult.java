package com.aas.goods.domain.repository.query.result;

import lombok.Getter;
import lombok.Setter;

/**
 * Domain Layer: Repository 실행 후 반환되는 순수 결과 DTO
 */
@Getter
@Setter
public class OptionMgmtQueryResult {
    private String optnCatNo;
    private String optnCatNm;
    private String optnCatRegGbCd;
    private String optnCatTypCd;
    private String entrNo;
    private String entrNm;
    private String useYn;
    private Integer sortSeq;
    private Integer totalCount;

    // 추가 옵션 상세 코드 관련 정보
    private String optnNo;
    private String optnNm;
    private String imgPathNm;
    private String imgFileNm;
    private String rgbVal;
    private String langCd;
    private String optnCatRegGbCdNm;
    private String optnCatTypCdNm;
    private String sysRegId;
    private String sysRegDtm;
    private String sysModId;
    private String sysModDtm;
}
