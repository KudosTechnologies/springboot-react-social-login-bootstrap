package ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomOAuth2OIdcService extends OidcUserService {

  private final CustomUserDetailsService customUserDetailsService;

  @Override
  public CustomUserDetails loadUser(OidcUserRequest userRequest)
      throws OAuth2AuthenticationException {
    OidcUser oidcUser = super.loadUser(userRequest);

    return customUserDetailsService.extractUserDetails(
        oidcUser, userRequest.getClientRegistration().getRegistrationId());
  }
}
