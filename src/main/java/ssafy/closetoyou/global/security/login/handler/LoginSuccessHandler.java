package ssafy.closetoyou.global.security.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ssafy.closetoyou.global.security.login.userdetail.CustomUserDetail;
import ssafy.closetoyou.refreshtoken.domain.RefreshToken;
import ssafy.closetoyou.refreshtoken.repository.RefreshTokenRepository;
import ssafy.closetoyou.user.domain.User;
import ssafy.closetoyou.global.common.response.SuccessResponse;
import ssafy.closetoyou.global.security.jwt.service.JwtService;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.access.expiration}")
    private String ACCESS_TOKEN_EXPIRATION;

    @Value("${COOKIE.EMAIL.EXPIRATION}")
    private int EMAIL_COOKIE_EXPIRATION;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        CustomUserDetail userDetails = (CustomUserDetail)authentication.getPrincipal();
        User user = userDetails.getUser();
        String accessToken = jwtService.createAccessToken(user.getEmail(), user.getUserId()); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(user.getEmail(), user.getUserId()); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

        // 자동 로그인을 위한 이메일 저장용 cookie
        Cookie emailCookie = new Cookie("email", user.getEmail());
        emailCookie.setMaxAge(EMAIL_COOKIE_EXPIRATION);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        RefreshToken issuedToken = RefreshToken.builder()
                .userId(user.getUserId())
                .refreshToken(refreshToken)
                .build();
        refreshTokenRepository.save(issuedToken);

        log.info("로그인에 성공하였습니다. 이메일 : {}", user.getEmail());
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", ACCESS_TOKEN_EXPIRATION);

        SuccessResponse<Object> apiResponse = new SuccessResponse<>("로그인애 성공하였습니다.");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
