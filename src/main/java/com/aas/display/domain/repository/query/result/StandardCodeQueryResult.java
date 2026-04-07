package com.aas.display.domain.repository.query.result;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StandardCodeQueryResult {
    private String grpCdNm;
    private String grpCd;
    private String cd;
    private String cdNm;
    private String cdDesc;
    private String useYn;
    private Integer sortSeq;
    private String ref1Val;
    private String ref2Val;
    private String ref3Val;
    private String ref4Val;
    private String ref5Val;
    private String ref6Val;
    private String ref7Val;
    private String sysRegId;
    private LocalDateTime sysRegDtm;
    private String sysModId;
    private LocalDateTime sysModDtm;
}
