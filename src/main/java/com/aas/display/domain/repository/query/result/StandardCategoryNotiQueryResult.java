package com.aas.display.domain.repository.query.result;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardCategoryNotiQueryResult {
    private String stdCtgNo;
    private String goodsNotiLisartCd;
    private String goodsNotiItemCd;
    private String notiItemNm;
    private Integer sortSeq;
    private String requiredYn; // 필요할 경우 사용
}
