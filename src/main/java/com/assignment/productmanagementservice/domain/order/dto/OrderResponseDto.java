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
    private List<OrderItemDto> orderItems;
    private Long deliveryFee;
    private Long totalAmount;
    // TODO 여기 쿠폰 어떻게 할지 - 쿠폰 적용 금액 나오게?
}
