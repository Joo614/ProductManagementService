package com.assignment.productmanagementservice.domain.product.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
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
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createAt;
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss" , iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modifiedAt;
}
