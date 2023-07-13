package com.assignment.productmanagementservice.domain.productPriceHistory.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private Long productPriceId;
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;
}
