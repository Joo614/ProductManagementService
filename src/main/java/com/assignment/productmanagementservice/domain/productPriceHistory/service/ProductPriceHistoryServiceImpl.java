package com.assignment.productmanagementservice.domain.productPriceHistory.service;

import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.domain.productPriceHistory.repository.JpaProductPriceHistoryRepository;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.data.domain.Page;
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

    // 특정 시점의 상품 가격 조회
    @Override
    public ProductPriceHistory findProductPriceAtSpecificTime(long productId, LocalDateTime timestamp, String userName) {
        memberService.findMember(userName);
        findProductPriceHistory(productId);

        Optional<ProductPriceHistory> optionalProductPriceHistory = jpaProductPriceHistoryRepository.findByProductIdAndModifiedAt(productId, timestamp);

        if (optionalProductPriceHistory.isPresent()) {
            return optionalProductPriceHistory.get();
        } else {
            throw new CustomLogicException(ExceptionCode.PRODUCT_PRICE_HISTORY_NONE);
        }
    }

    // 상품별 가격 Hsitory 조회
    @Override
    public Page<ProductPriceHistory> findAllProductPriceGroupedByProductId(int page, int size) {
        return jpaProductPriceHistoryRepository.findAllGroupedByProductId(page, size);
    }


    // 검증 로직
    private void findProductPriceHistory(long productId) {
        Optional<ProductPriceHistory> optionalProduct = jpaProductPriceHistoryRepository.findByProductId(productId);
        optionalProduct.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.PRODUCT_NONE));
    }

}
