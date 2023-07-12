package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.domain.productPriceHistory.repository.JpaProductPriceHistoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {
    private final JpaProductPriceHistoryRepository jpaProductPriceHistoryRepository;

    public ProductPriceHistoryServiceImpl(JpaProductPriceHistoryRepository jpaProductPriceHistoryRepository) {
        this.jpaProductPriceHistoryRepository = jpaProductPriceHistoryRepository;
    }

    //    @Override
//    public Page<ProductPrice> findAllProductPrice(int page, int size) {
//        return jpaProductPriceRepository.findAll(PageRequest.of(page, size,
//                Sort.by("productPriceId").descending()));
//    }
    @Override
    public List<ProductPriceHistory> findAllProductPriceGroupedByProductId() {
        List<ProductPriceHistory> groupedResults = jpaProductPriceHistoryRepository.findAllGroupedByProductId();
        return groupedResults;
    }
}
