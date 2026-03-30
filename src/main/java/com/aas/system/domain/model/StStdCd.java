package com.aas.system.domain.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class StStdCd {
    private String grpCd;    // 그룹코드
    private String cd;       // 코드
    private String cdNm;     // 코드명
    private String cdDesc;   // 코드설명
    private String useYn;    // 사용여부
    private Integer sortSeq; // 정렬순서
    
    private String ref1Val;  // 참조1값
    private String ref2Val;  // 참조2값
    private String ref3Val;  // 참조3값
    private String ref4Val;  // 참조4값
    private String ref5Val;  // 참조5값
    private String ref6Val;  // 참조6값
    private String ref7Val;  // 참조7값

    // Base Entity fields
    private String sysRegId;
    private LocalDateTime sysRegDtm;
    private String sysModId;
    private LocalDateTime sysModDtm;
}
