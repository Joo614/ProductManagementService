package com.assignment.productmanagementservice.domain.member.dto;

import com.assignment.productmanagementservice.domain.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@Builder
public class MemberDto {
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    @Builder
    public static class Post {
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+$")
        private String email;
        @NotNull
        private String password;
        @NotNull
        @Pattern(regexp = "^[가-힣]{2,4}$")
        private String name;
        @NotNull
        private String nickname;
        @NotNull
        private Member.Gender genders;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String email;
        private String name;
        private String nickname;
        private Member.Gender genders;
        private Member.MemberStatus memberStatus;
        private Member.MemberRole memberRole;
    }

}
