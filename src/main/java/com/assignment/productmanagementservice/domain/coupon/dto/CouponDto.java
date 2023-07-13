package com.assignment.productmanagementservice.domain.coupon.dto;

import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
    public static class CouponPost { // 쿠폰 생성 시 요청으로 들어와야할 Dto
        @NonNull
        private String couponName;
        @NonNull
        private BigDecimal discountRate;
        @NonNull
        private CouponType type;
        @NonNull
        private CouponScope scope;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class OrderPost { // 주문 시 적용할 쿠폰 고르는 Dto
        @NonNull
        private String couponName;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long couponId;
        private String couponName;
        private BigDecimal discountRate;
        private BigDecimal discountAmount;
        private CouponType type;
        private CouponScope scope;
    }
}
