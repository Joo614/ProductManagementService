package com.assignment.productmanagementservice.auth.filter;

import com.assignment.productmanagementservice.auth.dto.LoginDto;
import com.assignment.productmanagementservice.auth.service.RefreshService;
import com.assignment.productmanagementservice.auth.token.AuthToken;
import com.assignment.productmanagementservice.auth.token.AuthTokenProvider;
import com.assignment.productmanagementservice.domain.member.entity.Member;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import com.assignment.productmanagementservice.grobal.response.ErrorResponder;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

// 로그인 요청을 처리하고 인증이 성공하면 JWT 토큰을 생성하여 응답 헤더에 추가하는 역할
// 실제로 로그인 요청을 처리하는 필터는 UsernamePasswordAuthenticationFilter
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshService refreshService;


    public JwtAuthenticationFilter(AuthTokenProvider authTokenProvider, AuthenticationManager authenticationManager, RefreshService refreshService) {
        this.authTokenProvider = authTokenProvider;
        this.authenticationManager = authenticationManager;
        this.refreshService = refreshService;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        LoginDto loginDto = null;
        try {
            loginDto = gson.fromJson(request.getReader(), LoginDto.class);
        } catch (IOException e) {
            throw new CustomLogicException(ExceptionCode.INVALID_ELEMENT);
        }
        if(loginDto == null) {
            try {
                ErrorResponder.sendErrorResponse(response, HttpStatus.BAD_REQUEST);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, javax.servlet.FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Member member = (Member) authResult.getPrincipal();
        AuthToken accessToken = authTokenProvider.createAccessToken(member.getEmail() , member.getRoles().stream().map(role -> role.getRole().name()).collect(Collectors.toList()));
        AuthToken refreshToken = authTokenProvider.createRefreshToken(member.getEmail());
        response.addHeader("Authorization", "Bearer " + accessToken.getToken());
        response.addHeader("RefreshToken", "Bearer " + refreshToken.getToken());
        refreshService.saveRefreshToken(member.getEmail(), refreshToken);
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}
