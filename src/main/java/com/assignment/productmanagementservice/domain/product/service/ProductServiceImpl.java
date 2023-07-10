package com.assignment.productmanagementservice.domain.product.service;

import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.repository.JpaProductRepository;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import com.assignment.productmanagementservice.grobal.utils.CustomBeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        return jpaProductRepository.save(updateProduct);
    }

    @Override
    public Product findProductPriceAtSpecificTime(long productId) {
        return null;
    }

    @Override
    public void deleteProduct(String userName, long productId) {
        memberService.findMember(userName);

        Product findProduct = findProduct(productId);

        jpaProductRepository.delete(findProduct);
    }

    // 검증 로직
    public Product findProduct(long productId) {
        return verifyProduct(productId);
    }

    private Product verifyProduct(long productId) {
        Optional<Product> optionalProduct = jpaProductRepository.findById(productId);
        return optionalProduct.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.PRODUCT_NONE));
    }
}
