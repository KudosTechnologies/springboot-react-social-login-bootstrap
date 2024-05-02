package ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2;

import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.OAuth2Provider;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.UserRole;

@Service
public class GoogleOAuth2UserInfoExtractor implements OAuth2UserInfoExtractor {

  @Override
  public CustomUserDetails extractUserInfo(OAuth2User oAuth2User) {
    CustomUserDetails customUserDetails = new CustomUserDetails();
    customUserDetails.setUsername(retrieveAttr("email", oAuth2User));
    customUserDetails.setName(retrieveAttr("name", oAuth2User));
    customUserDetails.setEmail(retrieveAttr("email", oAuth2User));
    customUserDetails.setAvatarUrl(retrieveAttr("picture", oAuth2User));
    customUserDetails.setProvider(OAuth2Provider.GOOGLE);
    customUserDetails.setAttributes(oAuth2User.getAttributes());
    customUserDetails.setAuthorities(
        Collections.singletonList(new SimpleGrantedAuthority(UserRole.USER.name())));
    return customUserDetails;
  }

  @Override
  public boolean accepts(String registrationId) {
    return OAuth2Provider.GOOGLE.name().equalsIgnoreCase(registrationId);
  }

  private String retrieveAttr(String attr, OAuth2User oAuth2User) {
    Object attribute = oAuth2User.getAttributes().get(attr);
    return attribute == null ? "" : attribute.toString();
  }
}
