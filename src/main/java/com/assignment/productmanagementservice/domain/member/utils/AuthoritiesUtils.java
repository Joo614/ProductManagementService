package com.assignment.productmanagementservice.domain.member.utils;

import com.assignment.productmanagementservice.domain.member.entity.AuthoritiesEntity;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AuthoritiesUtils {
    public static Set<String> ADMINS_EMAIL;

    @Value("${admin.email}")
    public void setKey(String value) {
        ADMINS_EMAIL = Set.of(Arrays.stream(value.split(",")).map(String::trim).toArray(String[]::new));
    }

    public static List<String> createRoles(String email) {
        if (ADMINS_EMAIL != null && ADMINS_EMAIL.contains(email)) {
            return Stream.of(Member.MemberRole.values())
                    .map(Member.MemberRole::name)
                    .toList();
        }

        return List.of(Member.MemberRole.CUSTOMER.name());
    }
    public static List<AuthoritiesEntity> createAuthorities(Member member) {
        return createRoles(member.getEmail()).stream()
                .map(role -> new AuthoritiesEntity(member,role))
                .toList();
    }

    public static List<GrantedAuthority> getAuthorities(List<String> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    public static List<GrantedAuthority> getAuthoritiesByEntity(List<AuthoritiesEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
                .collect(Collectors.toList());
    }

}
