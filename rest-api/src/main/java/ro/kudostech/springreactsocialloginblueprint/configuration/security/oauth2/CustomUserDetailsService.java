package ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ro.kudostech.springreactsocialloginblueprint.configuration.security.WebSecurityConfig;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.UserService;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
  private final UserService userService;
  private final List<OAuth2UserInfoExtractor> oAuth2UserInfoExtractors;

  public CustomUserDetails extractUserDetails(OAuth2User oAuth2User, String registrationId) {
    Optional<OAuth2UserInfoExtractor> oAuth2UserInfoExtractorOptional =
        oAuth2UserInfoExtractors.stream()
            .filter(oAuth2UserInfoExtractor -> oAuth2UserInfoExtractor.accepts(registrationId))
            .findFirst();
    if (oAuth2UserInfoExtractorOptional.isEmpty()) {
      throw new InternalAuthenticationServiceException("The OAuth2 provider is not supported yet");
    }

    CustomUserDetails userDetails =
        oAuth2UserInfoExtractorOptional.get().extractUserInfo(oAuth2User);
    User user = upsertUser(userDetails);
    userDetails.setId(user.id());
    return userDetails;
  }

  private User upsertUser(CustomUserDetails customUserDetails) {
    Optional<User> userOptional = userService.getUserByEmail(customUserDetails.getUsername());
    if (userOptional.isEmpty()) {
      User user =
          User.builder()
              .email(customUserDetails.getEmail())
              .provider(customUserDetails.getProvider())
              .imageUrl(customUserDetails.getAvatarUrl())
              .role(WebSecurityConfig.USER)
              .build();
      return userService.saveUser(user);
    }
    return userOptional.get();
  }
}
