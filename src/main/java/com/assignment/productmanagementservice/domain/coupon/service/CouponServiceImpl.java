package com.assignment.productmanagementservice.domain.coupon.service;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.repository.JpaCouponRepository;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CouponServiceImpl implements CouponService {
    private final JpaCouponRepository jpaCouponRepository;
    private final MemberService memberService;
    private final ProductService productService;

    public CouponServiceImpl(JpaCouponRepository jpaCouponRepository, MemberService memberService, ProductService productService) {
        this.jpaCouponRepository = jpaCouponRepository;
        this.memberService = memberService;
        this.productService = productService;
    }

    // 쿠폰 생성
    @Override
    public Coupon createCoupon(Coupon coupon, String userName) {
        memberService.findMember(userName);

        if (coupon.getScope() == CouponScope.SPECIFIC_PRODUCT && coupon.getSpecificProductId() != null) {
            productService.findProduct(coupon.getSpecificProductId());
        }

        return jpaCouponRepository.save(coupon);
    }

    // 검증 로직
    @Override
    public Coupon findCoupon(long couponId) {
        return verifyCoupon(couponId);
    }

    private Coupon verifyCoupon(long couponId) {
        Optional<Coupon> optionalProduct = jpaCouponRepository.findById(couponId);
        return optionalProduct.orElseThrow(() ->
                new CustomLogicException(ExceptionCode.COUPON_NONE));
    }
}
