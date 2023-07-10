package com.assignment.productmanagementservice.domain.coupon.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CouponRepositoryImpl implements CouponRepository {
}
