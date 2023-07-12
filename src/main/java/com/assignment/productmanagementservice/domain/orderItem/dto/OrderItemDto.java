package com.assignment.productmanagementservice.domain.orderItem.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class OrderItemDto {
    private Long productId; // TODO mapper에서 넣어주기
    private Long quantity;
}
