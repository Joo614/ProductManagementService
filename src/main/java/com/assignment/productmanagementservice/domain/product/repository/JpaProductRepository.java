package com.assignment.productmanagementservice.domain.product.repository;


import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long>, ProductRepository {
}
