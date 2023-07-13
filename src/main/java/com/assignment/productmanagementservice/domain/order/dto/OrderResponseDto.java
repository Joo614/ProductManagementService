package com.assignment.productmanagementservice.domain.order.dto;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderResponseDto {
    private Long orderId;
    private List<OrderItemDto.Response> orderItems;
    private Long deliveryFee;
    private Long totalAmount;
    private Long paymentAmount;
    private CouponDto.Response appliedCoupon; // 적용한 쿠폰 정보
}
