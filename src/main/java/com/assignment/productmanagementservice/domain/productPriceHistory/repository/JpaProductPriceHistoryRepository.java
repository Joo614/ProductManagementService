package com.assignment.productmanagementservice.domain.productPriceHistory.repository;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long>, ProductPriceHistoryRepository {
}
