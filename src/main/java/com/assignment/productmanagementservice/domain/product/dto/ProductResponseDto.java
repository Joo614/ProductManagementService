package com.assignment.productmanagementservice.domain.product.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private Long price;
}
