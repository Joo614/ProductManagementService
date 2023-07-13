package com.assignment.productmanagementservice.domain.product.controller;

import com.assignment.productmanagementservice.domain.product.dto.ProductRequestDto;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.mapper.ProductMapper;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import com.assignment.productmanagementservice.grobal.response.MultiResponse;
import com.assignment.productmanagementservice.grobal.response.SingleResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, ProductMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }

    // 상품 생성
    @PostMapping()
    public ResponseEntity postProduct(@AuthenticationPrincipal User authMember,
                                      @Valid @RequestBody ProductRequestDto.ProductPost requestBody) {
        Product product = productService.createProduct(mapper.productPostDtoToProduct(requestBody), authMember.getUsername());
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

    // 상품 삭제
    @DeleteMapping("/{product_id}")
    public ResponseEntity deleteProduct(@AuthenticationPrincipal User authMember,
                                        @PathVariable("product_id") @Positive long productId) {
        productService.deleteProduct(authMember.getUsername(), productId);
        return ResponseEntity.noContent().build();
    }

    // 모든 상품 조회
    @GetMapping
    public ResponseEntity getAllProducts(@Positive @RequestParam int page,
                                         @Positive @RequestParam int size) {
        Page<Product> pageProduct = productService.findAllProduct(page - 1, size);
        List<Product> products = pageProduct.getContent();
        return new ResponseEntity<>(
                new MultiResponse<>(mapper.productsToProductResponses(products), pageProduct), HttpStatus.OK);
    }
}
