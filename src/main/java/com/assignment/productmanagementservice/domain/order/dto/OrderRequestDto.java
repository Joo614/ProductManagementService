package com.assignment.productmanagementservice.domain.order.dto;

import com.assignment.productmanagementservice.domain.orderItem.dto.OrderItemDto;
import lombok.*;

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
        private List<OrderItemDto> orderItems;
        // TODO 쿠폰 적용 고르기
    }
}
