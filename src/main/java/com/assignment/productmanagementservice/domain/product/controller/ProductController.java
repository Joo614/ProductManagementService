package com.assignment.productmanagementservice.domain.product.controller;

import com.assignment.productmanagementservice.domain.product.dto.ProductRequestDto;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.mapper.ProductMapper;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import com.assignment.productmanagementservice.grobal.response.SingleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.text.ParseException;

@RestController
@Validated
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;
    private static final String BASE_URL = "/api/v1/products";

    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    // 상품 생성
    @PostMapping()
    public ResponseEntity postProduct(@AuthenticationPrincipal User authMember,
                                      @Valid @RequestBody ProductRequestDto.ProductPost requestBody) {
        Product product = productService.createProduct(mapper.productPostDtoToProduct(requestBody), authMember.getUsername());
//        return ResponseEntity.created(URI.create(BASE_URL)).build(); // TODO
        return new ResponseEntity<>(new SingleResponse<>(mapper.productToProductResponseDto(product)), HttpStatus.CREATED);
    }

    // 상품 가격 수정
    @PatchMapping("/{product_id}")
    public ResponseEntity patchProduct(@AuthenticationPrincipal User authMember,
                                       @PathVariable("product_id") @Positive long productId,
                                      @Valid @RequestBody ProductRequestDto.ProductPatch requestBody) {
        Product product = productService.updateProductPrice(authMember.getUsername(), productId, mapper.productPatchDtoToProduct(requestBody));
        return ResponseEntity.ok(new SingleResponse<>(mapper.productToProductResponseDto(product)));
    }

    // 특정 시점의 상품 가격 조회
//    @GetMapping("/{product_id}")
//    public ResponseEntity getProductPriceAtSpecificTime(){
//        return ResponseEntity.ok();
//    }

    // 상품 삭제
    @DeleteMapping("/{product_id}")
    public ResponseEntity deleteProduct(@AuthenticationPrincipal User authMember,
                                        @PathVariable("product_id") @Positive long productId) {
        productService.deleteProduct(authMember.getUsername(), productId);
        return ResponseEntity.noContent().build();
    }
}
