package com.assignment.productmanagementservice.domain.product.service;

import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.repository.JpaProductRepository;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.domain.productPriceHistory.repository.JpaProductPriceHistoryRepository;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import com.assignment.productmanagementservice.grobal.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final JpaProductRepository jpaProductRepository;
    private final JpaProductPriceHistoryRepository jpaProductPriceHistoryRepository;
    private final MemberService memberService;
    private final CustomBeanUtils<Product> beanUtils;

    public ProductServiceImpl(JpaProductRepository jpaProductRepository, JpaProductPriceHistoryRepository jpaProductPriceHistoryRepository, MemberService memberService, CustomBeanUtils<Product> beanUtils) {
        this.jpaProductRepository = jpaProductRepository;
        this.jpaProductPriceHistoryRepository = jpaProductPriceHistoryRepository;
        this.memberService = memberService;
        this.beanUtils = beanUtils;
    }

    @Override
    public Product createProduct(Product product, String userName) {
        memberService.findMember(userName);

        Product savedProduct = jpaProductRepository.save(product); // Product 저장

        // productPrice 레포에 저장
        ProductPriceHistory productPriceHistory = new ProductPriceHistory();
        productPriceHistory.setProductId(savedProduct.getProductId());
        productPriceHistory.setProductName(savedProduct.getProductName());
        productPriceHistory.setPrice(product.getPrice());
        productPriceHistory.setCreatedAt(LocalDateTime.now());
        productPriceHistory.setModifiedAt(LocalDateTime.now());
        jpaProductPriceHistoryRepository.save(productPriceHistory); // ProductPrice 저장

        return savedProduct;
    }

    @Override
    public Product updateProductPrice(String userName, long productId, Product product) {
        memberService.findMember(userName);

        Product findProduct = findProduct(productId);

        Product updateProduct = beanUtils.copyNonNullProperties(product, findProduct);

        Product savedProduct = jpaProductRepository.save(updateProduct); // Product 저장

        // productPrice 레포에 저장
        ProductPriceHistory productPriceHistory = new ProductPriceHistory();
        productPriceHistory.setProductId(updateProduct.getProductId());
        productPriceHistory.setProductName(updateProduct.getProductName());
        productPriceHistory.setPrice(product.getPrice());
        productPriceHistory.setCreatedAt(updateProduct.getCreatedAt());
        productPriceHistory.setModifiedAt(LocalDateTime.now());
        jpaProductPriceHistoryRepository.save(productPriceHistory); // ProductPrice 저장

        return savedProduct;
    }

    @Override
    public void deleteProduct(String userName, long productId) {
        memberService.findMember(userName);

        Product findProduct = findProduct(productId);

        jpaProductRepository.delete(findProduct);
    }

    // 검증 로직
    @Override
    public Product findProduct(long productId) {
        return verifyProduct(productId);
    }

    private Product verifyProduct(long productId) {
        Optional<Product> optionalProduct = jpaProductRepository.findById(productId);
        return optionalProduct.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.PRODUCT_NONE));
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        List<Product> products = new ArrayList<>();

        for (Long productId : productIds) {
            Product product = verifyProduct(productId);
            products.add(product);
        }
        return products;
    }

    // TODO 검증용
    @Override
    public Page<Product> findAllProduct(int page, int size) {
        return jpaProductRepository.findAll(PageRequest.of(page, size,
                Sort.by("productId").descending()));
    }
}
