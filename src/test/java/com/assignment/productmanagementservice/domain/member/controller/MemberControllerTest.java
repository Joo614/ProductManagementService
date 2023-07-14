package com.assignment.productmanagementservice.domain.member.controller;

import com.assignment.productmanagementservice.common.token.GeneratedToken;
import com.assignment.productmanagementservice.domain.AbstractControllerTest;
import com.assignment.productmanagementservice.domain.member.dto.MemberDto;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.factory.MemberFactory;
import com.assignment.productmanagementservice.domain.member.mapper.MemberMapper;
import com.assignment.productmanagementservice.domain.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import static com.assignment.productmanagementservice.domain.member.factory.MemberFactory.createMemberPostDto;
import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getRequestPreProcessor;
import static com.assignment.productmanagementservice.utils.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends AbstractControllerTest {
    private final String BASE_URL = "/api/v1/members";
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("회원가입")
    @WithMockUser(username = "admin@gmail.com", roles = "ADMIN")
    void createMember() throws Exception {
        // given
        MemberDto.Post post = createMemberPostDto();
        String json = gson.toJson(post);
        Member expected = Member.builder()
                .email(post.getEmail())
                .password(post.getPassword())
                .name(post.getName()).build();
        given(memberService.createMember(any())).willReturn(expected);

        // when
        ResultActions resultActions = mockMvc.perform(post(BASE_URL + "/signup")
                        .contentType("application/json")
                        .with(csrf())
                        .content(json))
                .andExpect(status().isCreated());

        // then
        resultActions.andDo(print());
        resultActions.andDo(document("member-create",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestFields(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름 실명"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                        fieldWithPath("genders").type(JsonFieldType.STRING).description("성별 : MAN , WOMAN")
                )
        ));
    }

    @Test
    @DisplayName("회원 정보 조회")
    @WithMockUser
    void getMember() throws Exception {
        // given
        Member member = MemberFactory.createMember(new BCryptPasswordEncoder());
        given(memberService.findMemberDto(any())).willReturn(MemberFactory.createMemberResponseDto(member));
        // when
        ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/{email}", member.getEmail())
                        .contentType("application/json")
                        .headers(GeneratedToken.getMockHeaderToken())
                        .with(csrf()))
                .andExpect(status().isOk());
        // then
        resultActions.andDo(print());
        resultActions.andDo(
                document("member-get",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("email").description("회원 이메일")
                        ),
                        getMemberResponseFieldsSnippet()
                ));
    }
    private static ResponseFieldsSnippet getMemberResponseFieldsSnippet() {
        return responseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("회원 정보"),
                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름 실명"),
                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("data.genders").type(JsonFieldType.STRING).description("성별"),
                fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태"),
                fieldWithPath("data.memberRole").type(JsonFieldType.STRING).description("로그인 권한 정보 : ADMIN(MART), CUSTOMER")
        );
    }
}