package com.assignment.productmanagementservice.domain.productPriceHistory.repository;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductPriceHistoryRepository {

    // TODO queryDsl로 변경
    @Query("SELECT p FROM ProductPriceHistory p GROUP BY p.productId, p.productPriceHistoryId")
    List<ProductPriceHistory> findAllGroupedByProductId();

    Optional<ProductPriceHistory> findByProductIdAndModifiedAt(long productId, LocalDateTime timestamp);

    Optional<ProductPriceHistory> findByProductId(long productId);
}

