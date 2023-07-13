package com.assignment.productmanagementservice.domain.order.dto;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import lombok.*;

import javax.validation.constraints.NotNull;
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
        @NotNull
        private List<OrderItemDto.Post> orderItems;
        private CouponDto.OrderPost coupon;
    }
}
