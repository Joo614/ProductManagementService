package com.assignment.productmanagementservice.domain.productPriceHistory.controller;

import com.assignment.productmanagementservice.domain.AbstractControllerTest;
import com.assignment.productmanagementservice.domain.productPriceHistory.dto.ProductPriceHistoryResponseDto;
import com.assignment.productmanagementservice.domain.productPriceHistory.entity.ProductPriceHistory;
import com.assignment.productmanagementservice.domain.productPriceHistory.factory.ProductPriceHistoryFactory;
import com.assignment.productmanagementservice.domain.productPriceHistory.mapper.ProductPriceHistoryMapper;
import com.assignment.productmanagementservice.domain.productPriceHistory.service.ProductPriceHistoryService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductPriceHistoryController.class)
public class ProductPriceHistoryControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/productPrices";
    @MockBean
    private ProductPriceHistoryService productPriceHistoryService;
    @MockBean
    private ProductPriceHistoryMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("특정 시점의 상품 가격 조회")
    @WithMockUser(username = "test@gmail.com", roles = "CUSTOMER")
    void getProductPriceAtSpecificTime() throws Exception {
        // given
        long productId = 1L;
        LocalDateTime timestamp = LocalDateTime.now();  // 현재 시간으로 설정
        ProductPriceHistory productPriceHistory = ProductPriceHistoryFactory.createProductPriceHistory();
        ProductPriceHistoryResponseDto responseDto = ProductPriceHistoryFactory.createProductPriceHistoryResponseDto(productPriceHistory);

        given(productPriceHistoryService.findProductPriceAtSpecificTime(productId, timestamp, "test@gmail.com"))
                .willReturn(productPriceHistory);
        given(mapper.productPriceToProductPriceDto(productPriceHistory)).willReturn(responseDto);

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{product_id}/specificTime", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("timestamp", timestamp.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer {your_jwt_token}"));  // JWT 토큰을 직접 설정

        // then
        actions.andExpect(status().isOk())
                .andDo(document("productPriceHistory-getProductPriceAtSpecificTime",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("product_id").description("상품 ID")
                        ),
                        requestParameters(
                                parameterWithName("timestamp").description("상품의 가격을 찾길 원하는 시점")
                                        .attributes(
                                                key("constraints")
                                                        .value("ISO 8601 형식 (예: 2023-07-14T10:30:00)"))
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("상품 가격 정보"),
                                fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("data.productName").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data.productPriceHistoryId").type(JsonFieldType.NUMBER).description("상품 가격 히스토리 ID"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("가격 정보 수정 시각")
                        )
                ));
    }

    @Test
    @DisplayName("모든 상품의 가격 History 조회")
    @WithMockUser
    void getProductPriceHistory() throws Exception {
        // given
        List<ProductPriceHistory> productPrices = ProductPriceHistoryFactory.createProductPriceHistoryList();
        Page<ProductPriceHistory> priceHistoryPage = new PageImpl<>(productPrices);
        List<ProductPriceHistoryResponseDto> responses = ProductPriceHistoryFactory.createProductPriceHistoryResponseDtoList(productPrices);

        given(productPriceHistoryService.findAllProductPriceGroupedByProductId(anyInt(), anyInt())).willReturn(priceHistoryPage);
        given(mapper.productPricesToProductPriceResponses(any())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/groupedByProductId")
                .param("page", "1")
                .param("size", "1")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andDo(document("productPriceHistory-getAll",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("상품 가격 히스토리 정보"),
                                fieldWithPath("data[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("data[].productName").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("data[].price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data[].productPriceHistoryId").type(JsonFieldType.NUMBER).description("상품 가격 히스토리 ID"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("가격 정보 수정 시각"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("요소의 총 개수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                        )
                ));
    }
}
