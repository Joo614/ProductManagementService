package com.assignment.productmanagementservice.auth.service;

import com.assignment.productmanagementservice.auth.entity.RefreshToken;
import com.assignment.productmanagementservice.auth.repository.RefreshTokenRepository;
import com.assignment.productmanagementservice.auth.token.AuthToken;
import com.assignment.productmanagementservice.auth.token.AuthTokenProvider;
import com.assignment.productmanagementservice.grobal.exception.CustomLogicException;
import com.assignment.productmanagementservice.grobal.exception.ExceptionCode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.List;

import static com.assignment.productmanagementservice.auth.utils.HeaderUtil.*;

@Component
@Transactional
public class RefreshService { // 토큰 갱신과 관련된 작업을 수행하는 서비스
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider authTokenProvider;

    public RefreshService(RefreshTokenRepository refreshTokenRepository,
                          AuthTokenProvider authTokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.authTokenProvider = authTokenProvider;
    }

    public void saveRefreshToken(String email, AuthToken authToken) {
        refreshTokenRepository.findById(email)
                .ifPresentOrElse(
                        refreshToken -> {
                            refreshToken.setToken(authToken.getToken());
                            refreshToken.setExpiryDate(authToken.getTokenClaims().getExpiration());
                        },
                        () -> {
                            RefreshToken refreshToken = RefreshToken.builder()
                                    .email(email)
                                    .token(authToken.getToken())
                                    .expiryDate(authToken.getTokenClaims().getExpiration())
                                    .build();
                            refreshTokenRepository.save(refreshToken);
                        }
                );

    }

    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        AuthToken accessToken = authTokenProvider.convertAuthToken(getAccessToken(request));
        validateTokenCheck(accessToken);
        String userEmail = accessToken.getExpiredTokenClaims().getSubject();
        RefreshToken refreshToken = refreshTokenRepository.findById(userEmail).orElseThrow(
                () -> new CustomLogicException(ExceptionCode.REFRESH_TOKEN_NOT_FOUND)
        );
        //AuthToken requestRefreshToken = authTokenProvider.convertAuthToken(getHeaderRefreshToken(request));
        validateRefreshTokenCheck(refreshToken, authTokenProvider.convertAuthToken(getHeaderRefreshToken(request)));
        AuthToken newAccessToken = authTokenProvider
                .createAccessToken(userEmail, (List<String>) accessToken.getExpiredTokenClaims().get("role"));
        response.addHeader("Authorization", "Bearer " + newAccessToken.getToken());
    }

    public void validateTokenCheck(AuthToken authToken) {
        if (!authToken.validateWithOutExpired()) {
            throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);
        }
        if (authToken.getExpiredTokenClaims() == null) {
            throw new CustomLogicException(ExceptionCode.TOKEN_NOT_EXPIRED);
        }
    }

    public void validateRefreshTokenCheck(RefreshToken refreshToken, AuthToken headerRefreshToken) {
        if (!headerRefreshToken.validate()) {
            throw new CustomLogicException(ExceptionCode.REFRESH_TOKEN_INVALID);
        }
        if (!refreshToken.getToken().equals(headerRefreshToken.getToken())) {
            throw new CustomLogicException(ExceptionCode.REFRESH_TOKEN_NOT_MATCH);
        }
    }

    // TODO 로그아웃
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        AuthToken accessToken = authTokenProvider.convertAuthToken(getAccessToken(request));
//        if (!accessToken.validate()) throw new CustomLogicException(ExceptionCode.TOKEN_INVALID);
//        String userEmail = accessToken.getTokenClaims().getSubject();
//        long time = accessToken.getTokenClaims().getExpiration().getTime() - System.currentTimeMillis();
//        redisUtils.setBlackList(accessToken.getToken(), userEmail, time);
//        refreshTokenRepository.deleteById(userEmail);
//    }
}
