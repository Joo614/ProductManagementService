package com.assignment.productmanagementservice.domain.product.mapper;

import com.assignment.productmanagementservice.domain.product.dto.ProductRequestDto;
import com.assignment.productmanagementservice.domain.product.dto.ProductResponseDto;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productPostDtoToProduct(ProductRequestDto.ProductPost requestBody);
    Product productPatchDtoToProduct(ProductRequestDto.ProductPatch requestBody);

    default ProductResponseDto productToProductResponseDto(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponseDto.builder()
                .productId(product.getProductId())
                .price(product.getPrice())
                .productName(product.getProductName())
                .createAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }


    default List<ProductResponseDto> productsToProductResponses(List<Product> products) {
        List<ProductResponseDto> responseDtos = products.stream()
                .map(this::productToProductResponseDto)
                .toList();
        return responseDtos;
    }
}
