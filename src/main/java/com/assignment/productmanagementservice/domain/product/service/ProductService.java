package com.assignment.productmanagementservice.domain.product.service;

import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product, String userName); // 상품 생성
    Product updateProductPrice(String userName, long productId, Product product); // 상품 가격 수정
    void deleteProduct(String userName, long productId); // 상품 삭제
    Page<Product> findAllProduct(int page, int size); // 모든 상품 조회

    // 검증 로직
    Product findProduct(long productId);
    void findProductsByIds(List<Long> productIds);



}
