package com.assignment.productmanagementservice.domain.coupon.controller;

import com.assignment.productmanagementservice.common.token.GeneratedToken;
import com.assignment.productmanagementservice.domain.AbstractControllerTest;
import com.assignment.productmanagementservice.domain.coupon.dto.CouponDto;
import com.assignment.productmanagementservice.domain.coupon.entity.Coupon;
import com.assignment.productmanagementservice.domain.coupon.factory.CouponFactory;
import com.assignment.productmanagementservice.domain.coupon.mapper.CouponMapper;
import com.assignment.productmanagementservice.domain.coupon.service.CouponService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CouponController.class)
public class CouponControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/coupons";
    @MockBean
    private CouponService couponService;
    @MockBean
    private CouponMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("쿠폰 생성")
    @WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
    void createCoupon() throws Exception {
        // given
        CouponDto.CouponPost post = CouponFactory.createCouponPostDto();
        String json = gson.toJson(post);

        Coupon expected = CouponFactory.createCoupon();
        CouponDto.Response response = CouponFactory.createCouponResponseDto(expected);

        given(couponService.createCoupon(any(),any())).willReturn(expected);
        given(mapper.couponToCouponResponseDto(any())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(json)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        // then
        actions.andExpect(status().isCreated())
                .andDo(document("coupon-create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("couponName").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("discountRate").type(JsonFieldType.NUMBER).description("할인율"),
                                fieldWithPath("discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("쿠폰 종류"),
                                fieldWithPath("scope").type(JsonFieldType.STRING).description("적용 범위"),
                                fieldWithPath("productId").type(JsonFieldType.NUMBER).optional()
                                        .description("쿠폰 적용할 상품 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("쿠폰 정보"),
                                fieldWithPath("data.couponId").type(JsonFieldType.NUMBER).description("쿠폰 ID"),
                                fieldWithPath("data.couponName").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("data.discountRate").type(JsonFieldType.NUMBER).description("할인율"),
                                fieldWithPath("data.discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("data.type").type(JsonFieldType.STRING).description("쿠폰 종류"),
                                fieldWithPath("data.scope").type(JsonFieldType.STRING).description("적용 범위"),
                                fieldWithPath("data.specificProductId").type(JsonFieldType.NUMBER).optional()
                                        .description("쿠폰 적용할 상품 ID")
                        )
                ));
    }
}
