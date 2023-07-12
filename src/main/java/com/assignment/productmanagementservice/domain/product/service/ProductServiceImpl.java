package com.assignment.productmanagementservice.domain.product.service;

import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.repository.JpaProductRepository;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import com.assignment.productmanagementservice.grobal.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final MemberService memberService;
    private final CustomBeanUtils<Product> beanUtils;

    public ProductServiceImpl(JpaProductRepository jpaProductRepository, MemberService memberService, CustomBeanUtils<Product> beanUtils) {
        this.jpaProductRepository = jpaProductRepository;
        this.memberService = memberService;
        this.beanUtils = beanUtils;
    }

    @Override
    public Product createProduct(Product product, String userName) {
        memberService.findMember(userName);

        return jpaProductRepository.save(product);
    }

    @Override
    public Product updateProductPrice(String userName, long productId, Product product) {
        memberService.findMember(userName);

        Product findProduct = findProduct(productId);

        Product updateProduct = beanUtils.copyNonNullProperties(product, findProduct);

        Product result = new Product();
        result.setPrice(updateProduct.getPrice());
        result.setProductName(updateProduct.getProductName());
        result.setModifiedAt(LocalDateTime.now());
        result.setCreatedAt(findProduct.getCreatedAt());

        // TODO 잘 되면 findProduct 객체에 저장하도록 해보기
        return jpaProductRepository.save(result);
    }

    @Override
    public Product findProductPriceAtSpecificTime(long productId, LocalDateTime timestamp, String userName) {
//        Product productPrice = productPriceRepository.findLatestPriceBeforeTimestamp(productId, timestamp)
//                .orElseThrow(() -> new NotFoundException("Product price not found for the given timestamp."));
//
        return null;
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
