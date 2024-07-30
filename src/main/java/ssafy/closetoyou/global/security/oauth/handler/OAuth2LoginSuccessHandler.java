package ssafy.closetoyou.global.security.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ssafy.closetoyou.refreshtoken.domain.RefreshToken;
import ssafy.closetoyou.refreshtoken.repository.RefreshTokenRepository;
import ssafy.closetoyou.global.security.jwt.service.JwtService;
import ssafy.closetoyou.global.security.oauth.CustomOAuth2User;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${SERVER.FRONT}")
    private String SERVER_FRONT;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성 후 반환
        } catch (Exception e) {
            throw e;
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getId());
        String refreshToken = jwtService.createRefreshToken(oAuth2User.getEmail(),oAuth2User.getId());

        RefreshToken savedRefreshToken =
                RefreshToken.builder()
                        .userId(oAuth2User.getId())
                        .refreshToken(refreshToken)
                        .build();
        refreshTokenRepository.save(savedRefreshToken);
        jwtService.setRefreshTokenHeader(response, refreshToken);
        response.sendRedirect(SERVER_FRONT+"?accessToken="+accessToken); // 프론트의 인덱스로 이동
    }
}