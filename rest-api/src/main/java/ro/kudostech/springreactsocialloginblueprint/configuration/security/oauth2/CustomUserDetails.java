package ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2;

import java.util.Collection;
import java.util.Map;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.OAuth2Provider;

@Data
public class CustomUserDetails implements UserDetails, OidcUser {

  private Long id;
  private String username;
  private String password;
  private String name;
  private String email;
  private String avatarUrl;
  private OAuth2Provider provider;
  private Collection<? extends GrantedAuthority> authorities;
  private Map<String, Object> attributes;

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public Map<String, Object> getClaims() {
    return Map.of();
  }

  @Override
  public OidcUserInfo getUserInfo() {
    return null;
  }

  @Override
  public OidcIdToken getIdToken() {
    return null;
  }
}
