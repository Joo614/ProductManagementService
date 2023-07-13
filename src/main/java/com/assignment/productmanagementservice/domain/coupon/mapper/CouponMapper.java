package com.assignment.productmanagementservice.domain.coupon.mapper;

import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.entity.CouponScope;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    default Coupon couponPostDtoToCoupon(CouponDto.CouponPost requestBody) {
        if (requestBody == null) {
            return null;
        }

        Coupon.CouponBuilder coupon = Coupon.builder();

        coupon.couponName(requestBody.getCouponName());
        coupon.discountRate(requestBody.getDiscountRate());
        coupon.discountAmount(requestBody.getDiscountAmount());
        coupon.scope(requestBody.getScope());
        coupon.type(requestBody.getType());
        if (requestBody.getScope() == CouponScope.SPECIFIC_PRODUCT && requestBody.getProductId() != null) {
            coupon.specificProductId(requestBody.getProductId());
        }

        return coupon.build();
    }
    default CouponDto.Response couponToCouponResponseDto(Coupon coupon) {
        if (coupon == null) {
            return null;
        }

        CouponDto.Response.ResponseBuilder response = CouponDto.Response.builder();

        response.couponId(coupon.getCouponId());
        response.couponName(coupon.getCouponName());
        response.discountRate(coupon.getDiscountRate());
        response.discountAmount(coupon.getDiscountAmount());
        response.type(coupon.getType());
        response.scope(coupon.getScope());
        if (coupon.getSpecificProductId() != null) {
            response.specificProductId(coupon.getSpecificProductId());
        }

        return response.build();
    }

}
