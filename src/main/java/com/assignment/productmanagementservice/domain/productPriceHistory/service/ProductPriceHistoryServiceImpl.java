package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.domain.productPriceHistory.repository.JpaProductPriceHistoryRepository;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService {
    private final JpaProductPriceHistoryRepository jpaProductPriceHistoryRepository;
    private final MemberService memberService;

    public ProductPriceHistoryServiceImpl(JpaProductPriceHistoryRepository jpaProductPriceHistoryRepository, MemberService memberService) {
        this.jpaProductPriceHistoryRepository = jpaProductPriceHistoryRepository;
        this.memberService = memberService;
    }

    @Override
    public ProductPriceHistory findProductPriceAtSpecificTime(long productId, LocalDateTime timestamp, String userName) {
        memberService.findMember(userName);
        findProductPriceHistory(productId); // 해당 productId를 가진 가격 내역이 있는지 검증

        Optional<ProductPriceHistory> optionalProductPriceHistory = jpaProductPriceHistoryRepository.findByProductIdAndModifiedAt(productId, timestamp);

        if (optionalProductPriceHistory.isPresent()) {
            return optionalProductPriceHistory.get();
        } else {
            throw new CustomLogicException(ExceptionCode.PRODUCT_PRICE_HISTORY_NONE);
        }
    }

    // 검증 로직
    @Override
    public ProductPriceHistory findProductPriceHistory(long productId) {
        return verifyProductPriceHistory(productId);
    }

    private ProductPriceHistory verifyProductPriceHistory(long productId) {
        Optional<ProductPriceHistory> optionalProduct = jpaProductPriceHistoryRepository.findByProductId(productId);
        return optionalProduct.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.PRODUCT_NONE));
    }

    // TODO 검증용
    @Override
    public List<ProductPriceHistory> findAllProductPriceGroupedByProductId() {
        List<ProductPriceHistory> groupedResults = jpaProductPriceHistoryRepository.findAllGroupedByProductId();
        return groupedResults;
    }
}
