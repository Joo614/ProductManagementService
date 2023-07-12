package com.assignment.productmanagementservice.domain.productPriceHistory.controller;

import com.assignment.productmanagementservice.domain.productPriceHistory.mapper.ProductPriceHistoryMapper;
import com.assignment.productmanagementservice.domain.productPriceHistory.dto.ProductPriceHistoryResponseDto;
import com.assignment.productmanagementservice.domain.productPriceHistory.service.ProductPriceHistoryService;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/productPrices")
public class ProductPriceController {
    private final ProductPriceHistoryService productPriceHistoryService;
    private final ProductPriceHistoryMapper mapper;

    public ProductPriceController(ProductPriceHistoryService productPriceHistoryService, ProductPriceHistoryMapper mapper) {
        this.productPriceHistoryService = productPriceHistoryService;
        this.mapper = mapper;
    }

    //    @GetMapping
//    public ResponseEntity getAllProductPrices(@Positive @RequestParam int page,
//                                              @Positive @RequestParam int size) {
//        Page<ProductPrice> pageProduct = productPriceService.findAllProductPrice(page - 1, size);
//        List<ProductPrice> products = pageProduct.getContent();
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(mapper.productPricesToProductPriceResponses(products), pageProduct), HttpStatus.OK);
//    }

    @GetMapping("/groupedByProductId")
    public ResponseEntity<List<ProductPriceHistoryResponseDto>> getAllProductPricesGroupedByProductId() {
        List<ProductPriceHistory> productPrices = productPriceHistoryService.findAllProductPriceGroupedByProductId();
        return new ResponseEntity<>(mapper.productPricesToProductPriceResponses(productPrices), HttpStatus.OK);
    }
}
