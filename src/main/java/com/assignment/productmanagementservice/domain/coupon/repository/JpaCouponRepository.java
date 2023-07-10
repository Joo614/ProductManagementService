package com.assignment.productmanagementservice.domain.coupon.repository;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends JpaRepository<Coupon, Long>, CouponRepository {
}
