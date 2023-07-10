package com.assignment.productmanagementservice.domain.coupon.entity;

import lombok.Getter;

public enum CouponType { // 쿠폰 적용 방법
    RATIO("비율 쿠폰"),
    FIXED("고정 쿠폰");

    @Getter
    private String status;

    CouponType(String status) {
        this.status = status;
    }
}
