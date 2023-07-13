package com.assignment.productmanagementservice.domain.productPriceHistory.dto;

import lombok.*;

import java.time.LocalDateTime;

// TODO DateTimeFormat
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class ProductPriceHistoryResponseDto {
    private Long productId;
    private String productName;
    private Long price;
    private Long productPriceId;
    private LocalDateTime modifiedAt;
}
