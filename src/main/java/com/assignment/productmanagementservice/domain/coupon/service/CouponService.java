package com.assignment.productmanagementservice.domain.coupon.service;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.order.entity.Order;

public interface CouponService {
    Coupon createCoupon(Coupon coupon, String userName);
    long calculateCouponDiscount(Long couponId, Order order);
    Coupon findCoupon(long couponId);
}
