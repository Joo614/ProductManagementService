package com.assignment.productmanagementservice.domain.order.dto;

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
        private List<OrderItemDto.Post> orderItems;
//        @Valid
//        private OrderItemDto.Post productId;
//        @Valid
//        private OrderItemDto.Post quantity;

        @Builder.Default
        private Long deliveryFee = 3000L; // 기본 배달비를 3000원으로 설정

        // TODO 쿠폰 적용 고르기
    }
}
