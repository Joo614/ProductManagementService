package com.assignment.productmanagementservice.domain.productPriceHistory.entity;

import com.assignment.productmanagementservice.grobal.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
// TODO indexing 하는 거 알아보기
public class ProductPriceHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productPriceHistoryId;

    // TODO 콜롬 추가
    private Long productId;
    private String productName;
    private Long price;
}
