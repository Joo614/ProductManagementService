package com.assignment.productmanagementservice.domain.product.service;

import com.assignment.productmanagementservice.domain.product.entity.Product;

public interface ProductService {
    Product createProduct(Product product, String userName); // 상품 생성
    Product updateProductPrice(String userName, long productId, Product product); // 상품 가격 수정
    Product findProductPriceAtSpecificTime(long productId); // 특정 시점의 상품 가격 조회
    void deleteProduct(String userName, long productId); // 상품 삭제
}
