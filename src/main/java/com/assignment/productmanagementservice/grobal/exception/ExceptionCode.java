package com.assignment.productmanagementservice.grobal.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    // 접근 권한이 없습니다.
    // 상품이 존재하지 않습니다.
    UNAUTHORIZED(403, "UNAUTHORIZED"),
    INVALID_ELEMENT(400, "INVALID_ELEMENT"),
    TOKEN_INVALID(401, "TOKEN_INVALID"),
    TOKEN_NOT_EXPIRED(400, "TOKEN_NOT_EXPIRED"),
    REFRESH_TOKEN_NOT_FOUND(400, "REFRESH_TOKEN_NOT_FOUND"),
    REFRESH_TOKEN_INVALID(400, "REFRESH_TOKEN_INVALID"),
    REFRESH_TOKEN_NOT_MATCH(400, "REFRESH_TOKEN_NOT_MATCH"),
    MEMBER_DUPLICATE(409, "MEMBER_DUPLICATE"),
    MEMBER_NONE(404, "MEMBER_NONE"),
    EMAIL_NONE(400, "EMAIL_NEEDED"),
    PRODUCT_NONE(404, "PRODUCT_NONE"),
    PRODUCT_PRICE_HISTORY_NONE(404, "PRODUCT_PRICE_HISTORY_NONE"),
    ORDER_NONE(404, "ORDER_NONE"),
    COUPON_NONE(404, "COUPON_NONE");

    @Getter
    private final int code;

    @Getter
    private final String message;

    ExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
