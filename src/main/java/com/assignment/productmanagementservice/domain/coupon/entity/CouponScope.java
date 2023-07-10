package com.assignment.productmanagementservice.domain.coupon.entity;

import lombok.Getter;

public enum CouponScope { // 쿠폰 적용 범위
    FULL_ORDER("주문 전체"),       // 주문 전체 (배달비 제외)
    SPECIFIC_PRODUCT("특정 상품 한정");  // 특정 상품 한정

    @Getter
    private String status;

    CouponScope(String status) {
        this.status = status;
    }
}
