package com.assignment.productmanagementservice.domain.coupon.mapper;

import com.assignment.productmanagementservice.domain.coupon.controller.CouponController;
import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    Coupon couponPostDtoToCoupon(CouponDto.CouponPost requestBody);
    CouponDto.Response couponToCouponResponseDto(Coupon coupon);
}
