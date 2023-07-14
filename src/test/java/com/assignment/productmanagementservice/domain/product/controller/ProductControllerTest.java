package com.assignment.productmanagementservice.domain.product.controller;

import com.assignment.productmanagementservice.common.token.GeneratedToken;
import com.assignment.productmanagementservice.domain.AbstractControllerTest;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.factory.MemberFactory;
import com.assignment.productmanagementservice.domain.product.dto.ProductRequestDto;
import com.assignment.productmanagementservice.domain.product.dto.ProductResponseDto;
import com.assignment.productmanagementservice.domain.product.entity.Product;
import com.assignment.productmanagementservice.domain.product.factory.ProductFactory;
import com.assignment.productmanagementservice.domain.product.mapper.ProductMapper;
import com.assignment.productmanagementservice.domain.product.service.ProductService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/products";
    @MockBean
    private ProductService productService;
    @MockBean
    private ProductMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("상품 생성")
    @WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
    void createProduct() throws Exception {
        // given
        ProductRequestDto.ProductPost post = ProductFactory.createProductPostDto();
        String json = gson.toJson(post);

        Product expected = ProductFactory.createProduct();
        ProductResponseDto response = ProductFactory.createProductResponseDto(expected);

        given(productService.createProduct(any(), any())).willReturn(expected);
        given(mapper.productToProductResponseDto(any())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(json)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        // then
        actions.andExpect(status().isCreated())
                .andDo(document("product-create",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        requestFields(
                                fieldWithPath("productName").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("상품 정보"),
                                fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("data.productName").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("상품 생성 시각"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("상품 정보 수정 시각")
                        )
                ));
    }

    @Test
    @DisplayName("상품 가격 수정")
    @WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
    void updateProduct() throws Exception {
        // given
        long productId = 1L;
        ProductRequestDto.ProductPatch patch = ProductFactory.createProductPatchDto();
        String json = gson.toJson(patch);

        Product expected = ProductFactory.createProduct();
        ProductResponseDto response = ProductFactory.createProductResponseDto(expected);

        given(mapper.productPatchDtoToProduct(any())).willReturn(new Product());
        given(productService.updateProductPrice(any(), anyLong(), any())).willReturn(new Product());
        given(mapper.productToProductResponseDto(any())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(patch(BASE_URL + "/{productId}", productId)
                .contentType("application/json")
                .content(json)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        // then
        actions.andExpect(status().isOk())
                .andDo(document("product-update",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("productId").description("상품 ID")
                        ),
                        requestFields(
                                fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("상품 정보"),
                                fieldWithPath("data.productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("data.productName").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("data.price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("상품 생성 시각"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("상품 정보 수정 시각")
                        )
                ));
    }

    @Test
    @DisplayName("상품 삭제")
    @WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
    void deleteProduct() throws Exception {
        // given
        long productId = 1L;
        Product product = ProductFactory.createProduct();
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());

        doNothing().when(productService).deleteProduct(member.getEmail(), product.getProductId());

        // when
        ResultActions actions = mockMvc.perform(delete(BASE_URL + "/{productId}", productId)
                .headers(GeneratedToken.getMockHeaderToken())
                .with(csrf()));

        // then
        actions.andExpect(status().isNoContent())
                .andDo(document("product-delete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT 토큰")
                        ),
                        pathParameters(
                                parameterWithName("productId").description("상품 ID")
                        )
                ));
    }

    @Test
    @DisplayName("모든 상품 조회")
    @WithMockUser(username = "test@gmail.com", roles = "CUSOTMER")
    void getProducts() throws Exception {
        // given
        List<Product> products = ProductFactory.createProductList();
        Page<Product> productPage = new PageImpl<>(products);
        List<ProductResponseDto> responses = ProductFactory.createProductResponseDtoList(products);

        given(productService.findAllProduct(anyInt(), anyInt())).willReturn(productPage);
        given(mapper.productsToProductResponses(any())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(get(BASE_URL)
                .param("page", "1")
                .param("size", "1"));

        // then
        actions.andExpect(status().isOk())
                .andDo(document("product-getAll",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("상품 정보"),
                                fieldWithPath("data[].productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                fieldWithPath("data[].productName").type(JsonFieldType.STRING).description("상품명"),
                                fieldWithPath("data[].price").type(JsonFieldType.NUMBER).description("가격"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("상품 생성 시각"),
                                fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("상품 정보 수정 시각"),
                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("요소의 총 개수"),
                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 개수")
                        )
                ));
    }
}
