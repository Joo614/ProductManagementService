package com.assignment.productmanagementservice.auth.dto;

import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.domain.member.utils.AuthoritiesUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Slf4j
public class MemberPrincipal extends Member implements UserDetails{
    public MemberPrincipal(Member member) {
        setEmail(member.getEmail());
        setPassword(member.getPassword());
        setRoles(member.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthoritiesUtils.getAuthoritiesByEntity(getRoles());
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return this.getMemberStatus().equals(MemberStatus.MEMBER_ACTIVE);
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getName() {
        return null;
    }
    public static MemberPrincipal create(Member member) {
        return new MemberPrincipal(member);
    }
}
