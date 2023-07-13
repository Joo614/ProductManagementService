package com.assignment.productmanagementservice.domain.coupon.service;

import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import com.assignment.productmanagementservice.domain.coupon.repository.JpaCouponRepository;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.product.entity.Product;
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

    public CouponServiceImpl(JpaCouponRepository jpaCouponRepository, MemberService memberService) {
        this.jpaCouponRepository = jpaCouponRepository;
        this.memberService = memberService;
    }

    // 쿠폰 생성
    @Override
    public Coupon createCoupon(Coupon coupon, String userName) {
        memberService.findMember(userName);

        return jpaCouponRepository.save(coupon);
    }

    // 쿠폰의 할인 금액을 계산하는 로직
    public long calculateCouponDiscount(Long couponId, Order order) {
        long totalDiscount = 0L; // 일단 할인 가격을 0이라고 하고

//        Coupon coupon = findCoupon(couponId); // 해당 쿠폰이 존재하는지 검증 후 가져오기
//
//        // 쿠폰 적용 범위에 따라 할인 금액 계산
//        CouponScope couponScope = coupon.getScope();
//        switch (couponScope) {
//            case FULL_ORDER:
//                totalDiscount = calculateDiscountForFullOrder(coupon, order);
//                // 배달비를 제외한 가격*수량에 쿠폰 비율 적용할건데
//                // if 비율 쿠폰이면, (가격*수량) - (가격*수량)*쿠폰 비율
//                // if 고정 쿠폰이면, (가격*수량) - 쿠폰 할인 가격
//                break;
//            case SPECIFIC_PRODUCT:
//                totalDiscount = calculateDiscountForSpecificProduct(coupon, order);
//                // 해당 쿠폰이 선택된 product에 적용할건데
//                // if 비율 쿠폰이면, (해당 product 가격) - (해당 product 가격)*쿠폰 비율
//                // if 고정 쿠폰이면, (해당 product 가격) - 쿠폰 할인 가격
//                break;
//            default:
//                break;
//        }

        return totalDiscount;
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
