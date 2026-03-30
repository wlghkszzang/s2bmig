package com.aas.display.application.queryservice.query.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StandardCategoryAttrQueryResponse {
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
