package com.assignment.productmanagementservice.domain.product.factory;

import com.assignment.productmanagementservice.domain.product.dto.ProductRequestDto;
import com.assignment.productmanagementservice.domain.product.dto.ProductResponseDto;
import com.assignment.productmanagementservice.domain.product.entity.Product;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductFactory {
    public static ProductRequestDto.ProductPost createProductPostDto() throws ParseException {
        return ProductRequestDto.ProductPost.builder()
                .productName("상품명")
                .price(5000L)
                .build();
    }
    public static ProductRequestDto.ProductPatch createProductPatchDto() throws ParseException {
        return ProductRequestDto.ProductPatch.builder()
                .price(5000L)
                .build();
    }
    public static Product createProduct() throws org.locationtech.jts.io.ParseException {
        return Product.builder()
                .productId(1L)
                .productName("상품명")
                .price(5000L)
                .build();
    }

    public static List<Product> createProductList() throws java.text.ParseException, org.locationtech.jts.io.ParseException {
        List<Product> productList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            productList.add(createProduct());
        }
        return productList;
    }

    public static ProductResponseDto createProductResponseDto(Product product) {
        LocalDateTime now = LocalDateTime.now();
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }

    public static List<ProductResponseDto> createProductResponseDtoList(List<Product> productList) {
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (Product product : productList) {
            productResponseDtoList.add(createProductResponseDto(product));
        }
        return productResponseDtoList;
    }
}
