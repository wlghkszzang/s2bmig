package com.aas.display.interfaces.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StandardDisplayCategoryConnectCudDto {
    private String dispCtgNo; // 전시카테고리 번호 등 필요한 속성
    private String stdCtgNo; 
    private String dispYn;
    // 기타 필요한 AS-IS 속성 유지
}
