package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;

import java.util.List;

public interface ProductPriceHistoryService {
//    Page<ProductPrice> findAllProductPrice(int page, int size);
    List<ProductPriceHistory> findAllProductPriceGroupedByProductId();
}
