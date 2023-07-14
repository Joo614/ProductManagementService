package com.assignment.productmanagementservice.domain.coupon.factory;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponType;

public class CouponFactory {
    public static CouponDto.CouponPost createCouponPostDto() {
        return CouponDto.CouponPost.builder()
                .couponName("쿠폰명")
                .discountRate(10L)
                .discountAmount(1000L)
                .type(CouponType.FIXED)
                .scope(CouponScope.FULL_ORDER)
                .productId(1L)
                .build();
    }

    public static Coupon createCoupon() {
        return Coupon.builder()
                .couponId(1L)
                .couponName("쿠폰명")
                .discountRate(10L)
                .discountAmount(1000L)
                .type(CouponType.FIXED)
                .scope(CouponScope.FULL_ORDER)
                .build();
    }

    public static CouponDto.Response createCouponResponseDto(Coupon coupon) {
        return CouponDto.Response.builder()
                .couponId(coupon.getCouponId())
                .couponName(coupon.getCouponName())
                .discountRate(coupon.getDiscountRate())
                .discountAmount(coupon.getDiscountAmount())
                .type(coupon.getType())
                .scope(coupon.getScope())
                .specificProductId(coupon.getSpecificProductId())
                .build();
    }
}
