package com.assignment.productmanagementservice.domain.productPriceHistory.controller;

import com.assignment.productmanagementservice.domain.productPriceHistory.mapper.ProductPriceHistoryMapper;
import com.assignment.productmanagementservice.domain.productPriceHistory.service.ProductPriceHistoryService;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.grobal.response.MultiResponse;
import com.assignment.productmanagementservice.grobal.response.SingleResponse;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/productPrices")
public class ProductPriceHistoryController {
    private final ProductPriceHistoryService productPriceHistoryService;
    private final ProductPriceHistoryMapper mapper;

    public ProductPriceHistoryController(ProductPriceHistoryService productPriceHistoryService, ProductPriceHistoryMapper mapper) {
        this.productPriceHistoryService = productPriceHistoryService;
        this.mapper = mapper;
    }

    // 특정 시점의 상품 가격 조회
    @GetMapping("/{product_id}/specificTime")
    public ResponseEntity getProductPriceAtSpecificTime(@AuthenticationPrincipal User authMember,
                                                        @PathVariable("product_id") @Positive long productId,
                                                        @RequestParam("timestamp") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp){
        ProductPriceHistory productPriceHistory = productPriceHistoryService.findProductPriceAtSpecificTime(productId, timestamp, authMember.getUsername());
        return ResponseEntity.ok(new SingleResponse<>(mapper.productPriceToProductPriceDto(productPriceHistory)));
    }

    // 상품별 가격 History 조회
    @GetMapping("/groupedByProductId")
    public ResponseEntity getAllProductPricesGroupedByProductId(@Positive @RequestParam int page,
                                                                @Positive @RequestParam int size) {
        Page<ProductPriceHistory> pageProductPrices = productPriceHistoryService.findAllProductPriceGroupedByProductId(page - 1, size);
        List<ProductPriceHistory> productPrices = pageProductPrices.getContent();
        return new ResponseEntity<>(
                new MultiResponse<>(mapper.productPricesToProductPriceResponses(productPrices), pageProductPrices), HttpStatus.OK);
    }
}
