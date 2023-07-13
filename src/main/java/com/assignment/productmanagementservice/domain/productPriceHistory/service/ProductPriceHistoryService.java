package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductPriceHistoryService {
    ProductPriceHistory findProductPriceAtSpecificTime(long productId, LocalDateTime timestamp, String userName); // 특정 시점의 상품 가격 조회
    List<ProductPriceHistory> findAllProductPriceGroupedByProductId(); // 상품별 가격 History 조회

}
