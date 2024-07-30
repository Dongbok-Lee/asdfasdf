package ssafy.closetoyou.global.security.oauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ssafy.closetoyou.global.security.oauth.OAuthAttributes;
import ssafy.closetoyou.user.domain.User;
import ssafy.closetoyou.user.infrastructure.UserEntity;
import ssafy.closetoyou.user.infrastructure.UserRepository;
import ssafy.closetoyou.global.security.oauth.CustomOAuth2User;
import ssafy.closetoyou.global.security.oauth.OauthServerType;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private static final String NAVER = "naver";
    private static final String KAKAO = "kakao";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OauthServerType oauthServerType = getSocialType(registrationId);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes extractAttributes = OAuthAttributes.of(oauthServerType, userNameAttributeName, attributes);

        User createdUser = getUser(extractAttributes);

        return new CustomOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                extractAttributes.getNameAttributeKey(),
                createdUser.getEmail(),
                createdUser.getUserId()
        );
    }

    private OauthServerType getSocialType(String registrationId) {
        if(NAVER.equals(registrationId)) {
            return OauthServerType.NAVER;
        }
        if(KAKAO.equals(registrationId)) {
            return OauthServerType.KAKAO;
        }
        return OauthServerType.GOOGLE;
    }

    private User getUser(OAuthAttributes attributes) {

        User findUserEntity = userRepository.findByEmail(attributes.getOauth2UserInfo().getEmail());

        if(findUserEntity == null) {
            return saveUser(attributes);
        }
        return findUserEntity;
    }

    private User saveUser(OAuthAttributes attributes) {
        User createdUserEntity = attributes.toModel(attributes.getOauth2UserInfo());
        return userRepository.saveUser(createdUserEntity);
    }
}