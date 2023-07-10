package com.assignment.productmanagementservice.grobal.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    // 접근 권한이 없습니다.
    // 상품이 존재하지 않습니다.
    UNAUTHORIZED(403, "UNAUTHORIZED"),
    PRODUCT_NONE(404, "PRODUCT_NONE");

    @Getter
    private final int code;

    @Getter
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
