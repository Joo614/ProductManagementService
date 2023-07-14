package com.assignment.productmanagementservice.domain.productPriceHistory.repository;//package com.assignment.productmanagementservice.domain.productPrice.repository;

import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.QProductPriceHistory;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class ProductPriceHistoryRepositoryImpl implements ProductPriceHistoryRepository{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final QProductPriceHistory qProductPriceHistory = QProductPriceHistory.productPriceHistory;

    public ProductPriceHistoryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    @Override
    public Page<ProductPriceHistory> findAllGroupedByProductId(int page, int size) {
        JPAQuery<ProductPriceHistory> query = queryFactory
                .selectDistinct(qProductPriceHistory)
                .from(qProductPriceHistory)
                .groupBy(qProductPriceHistory.productId, qProductPriceHistory.productPriceHistoryId)
                .offset(page * size)
                .limit(size);

        QueryResults<ProductPriceHistory> results = query.fetchResults();
        List<ProductPriceHistory> content = results.getResults();
        long totalElements = results.getTotal();

        return new PageImpl<>(content, PageRequest.of(page, size), totalElements);
    }

    @Override
    public Optional<ProductPriceHistory> findByProductIdAndModifiedAt(long productId, LocalDateTime timestamp) {
        JPAQuery<ProductPriceHistory> query = queryFactory
                .selectFrom(qProductPriceHistory)
                .where(qProductPriceHistory.productId.eq(productId)
                        .and(qProductPriceHistory.modifiedAt.eq(timestamp)))
                .limit(1); // 가장 최신의 하나만 가져오도록

        return Optional.ofNullable(query.fetchOne());
    }

    @Override
    public Optional<ProductPriceHistory> findByProductId(long productId) {
        JPAQuery<ProductPriceHistory> query = queryFactory
                .selectFrom(qProductPriceHistory)
                .where(qProductPriceHistory.productId.eq(productId))
                .orderBy(qProductPriceHistory.modifiedAt.desc())
                .limit(1);

        return Optional.ofNullable(query.fetchOne());
    }
}
