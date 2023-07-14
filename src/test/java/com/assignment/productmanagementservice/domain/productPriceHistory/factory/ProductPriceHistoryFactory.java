package com.assignment.productmanagementservice.domain.productPriceHistory.factory;

import com.assignment.productmanagementservice.domain.productPriceHistory.dto.ProductPriceHistoryResponseDto;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductPriceHistoryFactory {

    public static ProductPriceHistory createProductPriceHistory() throws org.locationtech.jts.io.ParseException {
        return ProductPriceHistory.builder()
                .productPriceHistoryId(1L)
                .productId(1L)
                .productName("상품명")
                .price(5000L)
                .build();
    }

    public static List<ProductPriceHistory> createProductPriceHistoryList() throws java.text.ParseException, org.locationtech.jts.io.ParseException {
        List<ProductPriceHistory> productPriceHistoryList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            productPriceHistoryList.add(createProductPriceHistory());
        }
        return productPriceHistoryList;
    }

    public static ProductPriceHistoryResponseDto createProductPriceHistoryResponseDto(ProductPriceHistory productPriceHistory) {
        LocalDateTime now = LocalDateTime.now();
        return ProductPriceHistoryResponseDto.builder()
                .productId(productPriceHistory.getProductId())
                .productName(productPriceHistory.getProductName())
                .price(productPriceHistory.getPrice())
                .productPriceHistoryId(1L)
                .modifiedAt(now)
                .build();
    }

    public static List<ProductPriceHistoryResponseDto> createProductPriceHistoryResponseDtoList(List<ProductPriceHistory> productPriceHistoryList) {
        List<ProductPriceHistoryResponseDto> productPriceHistoryResponseDtoList = new ArrayList<>();
        for (ProductPriceHistory productPriceHistory : productPriceHistoryList) {
            productPriceHistoryResponseDtoList.add(createProductPriceHistoryResponseDto(productPriceHistory));
        }
        return productPriceHistoryResponseDtoList;
    }
}
