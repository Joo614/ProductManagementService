package com.assignment.productmanagementservice.domain.productPriceHistory.repository;//package com.assignment.productmanagementservice.domain.productPrice.repository;

//import com.assignment.productmanagementservice.domain.productPrice.entity.ProductPriceHistory;
//import com.assignment.productmanagementservice.domain.productPrice.entity.QProductPrice;
//import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//@Repository
//@Transactional(readOnly = true)
//public class ProductPriceHistoryRepositoryImpl implements ProductPriceHistoryRepository{
//    private final EntityManager em;
//    private final JPAQueryFactory queryFactory;
////    private final QProductPrice productPrice = QProductPrice.productPrice;
//
//    public ProductPriceHistoryRepositoryImpl(EntityManager em) {
//        this.em = em;
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//    @Override
//    public List<ProductPriceHistory> findAllGroupedByProductId() {
//
//        // QueryDsl
//        JPAQuery<ProductPriceHistory> query = queryFactory.selectFrom(qProductPrice)
//        List<ProductPriceHistory> result = queryFactory
//                .selectFrom(qProductPrice)
//                .groupBy(qProductPrice.productId, qProductPrice.productPriceId)
//                .fetch();
//
//        return result;
//    }
//}
