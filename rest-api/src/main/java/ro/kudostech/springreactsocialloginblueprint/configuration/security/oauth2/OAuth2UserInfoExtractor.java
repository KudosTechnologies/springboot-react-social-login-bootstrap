package ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfoExtractor {

  CustomUserDetails extractUserInfo(OAuth2User oAuth2User);

  boolean accepts(String registrationId);
}
