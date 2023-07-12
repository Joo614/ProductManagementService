package com.assignment.productmanagementservice.domain.productPriceHistory.repository;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductPriceHistoryRepository {

    // TODO 일단 이걸로 하고 나중에 queryDsl로 바꾸기
    @Query("SELECT p FROM ProductPriceHistory p GROUP BY p.productId, p.productPriceHistoryId")
    List<ProductPriceHistory> findAllGroupedByProductId();
}

