package com.assignment.productmanagementservice.domain.coupon.service;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;

public interface CouponService {
    Coupon createCoupon(Coupon coupon, String userName);

    // 검증 로직
    Coupon findCoupon(long couponId);
}
