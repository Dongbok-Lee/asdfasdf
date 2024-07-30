package ssafy.closetoyou.global.security.oauth;

import lombok.Builder;
import lombok.Getter;
import ssafy.closetoyou.global.security.oauth.userInfo.GoogleOAuth2UserInfo;
import ssafy.closetoyou.global.security.oauth.userInfo.NaverOAuth2UserInfo;
import ssafy.closetoyou.user.domain.User;
import ssafy.closetoyou.global.security.oauth.userInfo.KakaoOAuth2UserInfo;
import ssafy.closetoyou.global.security.oauth.userInfo.OAuth2UserInfo;

import java.util.Map;


@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    public static OAuthAttributes of(OauthServerType oauthServerType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (oauthServerType == OauthServerType.NAVER) {
            return ofNaver(userNameAttributeName, attributes);
        }
        if (oauthServerType == OauthServerType.KAKAO) {
            return ofKakao(userNameAttributeName, attributes);
        }
        if (oauthServerType == OauthServerType.GOOGLE) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    public User toModel(OAuth2UserInfo oauth2UserInfo) {
        return User.builder()
                .email(oauth2UserInfo.getEmail())
                .build();
    }
}