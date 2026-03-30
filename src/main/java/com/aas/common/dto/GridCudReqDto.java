package com.aas.common.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 그리드 등록/수정/삭제 범용 Request DTO
 */
@Getter
@Setter
public class GridCudReqDto<T> {
    private List<T> create;
    private List<T> update;
    private List<T> delete;
}
