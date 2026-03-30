package com.aas.display.domain.repository.query.result;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StandardCategoryAttrQueryResult {
    private String stdCtgNo;
    private String attCd;
    private String attNm;
    private String attValTypCd;
    private String attValTypNm;
    private Integer dispSeq;
    private String useYn;
    private String attCdExpYn;
    private String sysModId;
    private LocalDateTime sysModDtm;
}
