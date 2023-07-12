package com.assignment.productmanagementservice.domain.productPriceHistory.mapper;

import com.assignment.productmanagementservice.domain.productPriceHistory.dto.ProductPriceHistoryResponseDto;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPriceHistoryMapper {
    default ProductPriceHistoryResponseDto productPriceToProductPriceDto(ProductPriceHistory productPriceHistory) {
        if (productPriceHistory == null) {
            return null;
        }
        return ProductPriceHistoryResponseDto.builder()
                .productId(productPriceHistory.getProductId())
                .productName(productPriceHistory.getProductName())
                .price(productPriceHistory.getPrice())
                .productPriceId(productPriceHistory.getProductPriceHistoryId())
                .modifiedAt(productPriceHistory.getModifiedAt())
                .build();
    }

    // TODO 검증용
    default List<ProductPriceHistoryResponseDto> productPricesToProductPriceResponses(List<ProductPriceHistory> products) {
        List<ProductPriceHistoryResponseDto> responseDtos = products.stream()
                .map(this::productPriceToProductPriceDto)
                .toList();
        return responseDtos;
    }
}
