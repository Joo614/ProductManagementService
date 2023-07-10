package com.assignment.productmanagementservice.auth.handler;

import com.assignment.productmanagementservice.grobal.response.SingleResponse;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
// 자격증명 성공시 실행되는 핸들러
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws java.io.IOException, javax.servlet.ServletException {
        response.setStatus(200);
        response.getWriter().write(new Gson().toJson(new SingleResponse<>("Login Success")));
        log.info("LOGIN SUCCESS : " + authentication.getName());
    }
}
