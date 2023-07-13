package com.assignment.productmanagementservice.domain.order.dto;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import lombok.*;

import javax.validation.Valid;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class OrderRequestDto {
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    public static class OrderPost {
        @NonNull
        private List<OrderItemDto.Post> orderItems;
        @Builder.Default
        private Long deliveryFee = 3000L; // 기본 배달비를 3000원으로 설정
        private CouponDto.OrderPost coupon;
    }
}
