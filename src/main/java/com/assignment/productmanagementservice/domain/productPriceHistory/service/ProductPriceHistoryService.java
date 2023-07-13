package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductPriceHistoryService {
    ProductPriceHistory findProductPriceAtSpecificTime(long productId, LocalDateTime timestamp, String userName); // 특정 시점의 상품 가격 조회
    ProductPriceHistory findProductPriceHistory(long productId);

    // TODO 검증용
    List<ProductPriceHistory> findAllProductPriceGroupedByProductId();
}
