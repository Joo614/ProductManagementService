package com.assignment.productmanagementservice.domain.product.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ProductRequestDto {
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class ProductPost {
        @NotNull
        private String productName;
        @NotNull
        private Long price;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    public static class ProductPatch {
        @NotNull
        private Long price;
    }
}
