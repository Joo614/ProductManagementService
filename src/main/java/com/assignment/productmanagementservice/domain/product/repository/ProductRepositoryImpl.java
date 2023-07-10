package com.assignment.productmanagementservice.domain.product.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class ProductRepositoryImpl implements ProductRepository {
}
