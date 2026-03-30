package com.aas.common.utils;

import lombok.*;
import java.util.List;

/**
 * 전사 공통 응답 포맷 (RspDto)
 * testproject의 표준 규격을 migproject에 이식함.
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RspDto<T> {
    private final boolean success;
    private final T data;
    private final ErrorResponse error;
    private final MetaResponse meta;

    public static <T> RspDto<T> ok(T data) {
        return RspDto.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static <T> RspDto<T> ok(T data, MetaResponse meta) {
        return RspDto.<T>builder()
                .success(true)
                .data(data)
                .meta(meta)
                .build();
    }

    public static <T> RspDto<T> fail(String code, String message) {
        return RspDto.<T>builder()
                .success(false)
                .error(new ErrorResponse(code, message))
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorResponse {
        private String code;
        private String message;
        private List<FieldError> details;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String value;
        private String reason;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MetaResponse {
        private int count;
        private long total;
    }
}
