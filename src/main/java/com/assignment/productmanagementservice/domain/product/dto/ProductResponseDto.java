package com.assignment.productmanagementservice.domain.product.dto;

import lombok.*;

import java.time.LocalDateTime;

// TODO DateTimeFormat
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private Long price;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
}
