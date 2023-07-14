package com.assignment.productmanagementservice.domain.order.controller;

import com.assignment.productmanagementservice.common.token.GeneratedToken;
import com.assignment.productmanagementservice.domain.AbstractControllerTest;
import com.assignment.productmanagementservice.domain.order.dto.OrderRequestDto;
import com.assignment.productmanagementservice.domain.order.dto.OrderResponseDto;
import com.assignment.productmanagementservice.domain.order.entity.Order;
import com.assignment.productmanagementservice.domain.order.factory.OrderFactory;
import com.assignment.productmanagementservice.domain.order.mapper.OrderMapper;
import com.assignment.productmanagementservice.domain.order.service.OrderService;
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
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/orders";
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("주문 생성")
    @WithMockUser(username = "test@gmail.com", roles = "CUSTOMER")
    void createProduct() throws Exception {
        // given
        OrderRequestDto.OrderPost post = OrderFactory.createOrderPost();
        String json = gson.toJson(post);

        Order expected = OrderFactory.createOrder();
        OrderResponseDto response = OrderFactory.createOrderResponseDto(expected);

        given(orderService.createOrder(any(), any())).willReturn(expected);
        given(mapper.orderToOrderResponseDto(any())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(json)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        // then
        actions.andExpect(status().isCreated())
                .andDo(document("order-create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("orderItems").type(JsonFieldType.ARRAY).description("주문 항목 리스트")
                                        .attributes(key("constraints").value("Not null")),
                                fieldWithPath("orderItems[].productId").type(JsonFieldType.NUMBER).description("상품 ID")
                                        .attributes(key("constraints").value("Not null")),
                                fieldWithPath("orderItems[].quantity").type(JsonFieldType.NUMBER).description("상품 수량")
                                        .attributes(key("constraints").value("Not null")),
                                fieldWithPath("coupon").type(JsonFieldType.OBJECT).optional().description("적용한 쿠폰 정보"),
                                fieldWithPath("coupon.couponId").type(JsonFieldType.NUMBER).optional().description("적용한 쿠폰 ID")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("상품 정보"),
                                fieldWithPath("data.orderId").type(JsonFieldType.NUMBER).description("주문 ID"),
                                fieldWithPath("data.orderItems").type(JsonFieldType.ARRAY).description("주문 항목 리스트"),
                                fieldWithPath("data.orderItems[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("data.orderItems[].quantity").type(JsonFieldType.NUMBER).description("상품 수량"),
                                fieldWithPath("data.deliveryFee").type(JsonFieldType.NUMBER).description("배송비"),
                                fieldWithPath("data.totalAmount").type(JsonFieldType.NUMBER).description("총 주문 금액"),
                                fieldWithPath("data.paymentAmount").type(JsonFieldType.NUMBER).description("결제 금액"),
                                fieldWithPath("data.appliedCoupon").type(JsonFieldType.OBJECT).optional().description("적용한 쿠폰 정보"),
                                fieldWithPath("data.appliedCoupon.couponId").type(JsonFieldType.NUMBER).description("쿠폰 ID"),
                                fieldWithPath("data.appliedCoupon.couponName").type(JsonFieldType.STRING).description("쿠폰 이름"),
                                fieldWithPath("data.appliedCoupon.discountRate").type(JsonFieldType.NUMBER).description("할인율"),
                                fieldWithPath("data.appliedCoupon.discountAmount").type(JsonFieldType.NUMBER).description("할인 금액"),
                                fieldWithPath("data.appliedCoupon.type").type(JsonFieldType.STRING).description("쿠폰 종류"),
                                fieldWithPath("data.appliedCoupon.scope").type(JsonFieldType.STRING).description("적용 범위"),
                                fieldWithPath("data.appliedCoupon.specificProductId").type(JsonFieldType.NUMBER).optional().description("쿠폰 적용할 상품 ID")
                        )
                ));
    }
}
