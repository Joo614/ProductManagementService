package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductPriceHistoryService {
    ProductPriceHistory findProductPriceAtSpecificTime(long productId, LocalDateTime timestamp, String userName); // 특정 시점의 상품 가격 조회
    Page<ProductPriceHistory> findAllProductPriceGroupedByProductId(int page, int size); // 상품별 가격 History 조회

}
