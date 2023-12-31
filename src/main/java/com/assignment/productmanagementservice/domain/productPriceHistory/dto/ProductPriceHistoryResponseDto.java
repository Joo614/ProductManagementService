package com.assignment.productmanagementservice.domain.productPriceHistory.dto;

import lombok.*;

import java.time.LocalDateTime;

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
    private Long productPriceHistoryId;
    private LocalDateTime modifiedAt;
}
