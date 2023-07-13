package com.assignment.productmanagementservice.domain.coupon.dto;

import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponType;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CouponDto {
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class CouponPost { // 쿠폰 생성 시 요청으로 들어와야할 Dto - Mart용
        @NonNull
        private String couponName;
        private Long discountRate; // 할인율
        private Long discountAmount; // 할인 금액
        @NonNull
        private CouponType type;
        @NonNull
        private CouponScope scope;
        private Long productId; // 쿠폰 적용할 상품 Id
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class OrderPost { // 주문 시 적용할 쿠폰 고르는 Dto - 고객용
        @NonNull
        private Long couponId;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long couponId;
        private String couponName;
        private Long discountRate;
        private Long discountAmount;
        private CouponType type;
        private CouponScope scope;
        private Long specificProductId;
    }
}
