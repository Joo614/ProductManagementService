package com.assignment.productmanagementservice.domain.order.dto;

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
//    private OrderItemDto.Response productId;
//    private OrderItemDto.Response quantity;
    private Long deliveryFee;
    private Long totalAmount;
    private String appliedCoupon; // 적용한 쿠폰
}
