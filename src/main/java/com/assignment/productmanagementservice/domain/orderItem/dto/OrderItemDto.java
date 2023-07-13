package com.assignment.productmanagementservice.domain.orderItem.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderItemDto {
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post {
        @NotNull
        private Long productId;
        @NotNull
        private Long quantity;

        // TDOO dto들 다 valid 설정했는지 체크
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long productId;
        private Long quantity;
    }
}
