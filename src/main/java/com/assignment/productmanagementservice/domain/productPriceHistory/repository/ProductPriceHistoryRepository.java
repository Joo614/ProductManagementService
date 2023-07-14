package com.assignment.productmanagementservice.domain.productPriceHistory.repository;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductPriceHistoryRepository {

    Page<ProductPriceHistory> findAllGroupedByProductId(int page, int size);

    Optional<ProductPriceHistory> findByProductIdAndModifiedAt(long productId, LocalDateTime timestamp);

    Optional<ProductPriceHistory> findByProductId(long productId);
}

