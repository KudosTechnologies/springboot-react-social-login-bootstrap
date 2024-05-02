package ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import ro.kudostech.springreactsocialloginblueprint.configuration.security.WebSecurityConfig;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.OAuth2Provider;

@Component
public class KeycloakOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

  @Override
  public CustomUserDetails extractUserInfo(OAuth2User oAuth2User) {
    CustomUserDetails customUserDetails = new CustomUserDetails();
    customUserDetails.setUsername(retrieveAttr("email", oAuth2User));
    customUserDetails.setName(retrieveAttr("name", oAuth2User));
    customUserDetails.setEmail(retrieveAttr("email", oAuth2User));
    customUserDetails.setProvider(OAuth2Provider.KEYCLOAK);
    customUserDetails.setAttributes(oAuth2User.getAttributes());
    customUserDetails.setAuthorities(
        Collections.singletonList(new SimpleGrantedAuthority(WebSecurityConfig.USER)));
    return customUserDetails;
  }

  @Override
  public boolean accepts(String registrationId) {
    return OAuth2Provider.KEYCLOAK.name().equalsIgnoreCase(registrationId);
  }

  private String retrieveAttr(String attr, OAuth2User oAuth2User) {
    Object attribute = oAuth2User.getAttributes().get(attr);
    return attribute == null ? "" : attribute.toString();
  }
}
