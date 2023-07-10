package com.assignment.productmanagementservice.domain.product.mapper;

import com.assignment.productmanagementservice.domain.product.dto.ProductRequestDto;
import com.assignment.productmanagementservice.domain.product.dto.ProductResponseDto;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productPostDtoToProduct(ProductRequestDto.ProductPost requestBody);
    Product productPatchDtoToProduct(ProductRequestDto.ProductPatch requestBody);
    ProductResponseDto productToProductResponseDto(Product product);
}
