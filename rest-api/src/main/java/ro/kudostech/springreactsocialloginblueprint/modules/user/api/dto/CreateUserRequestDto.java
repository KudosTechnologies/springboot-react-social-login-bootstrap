package ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.apache.commons.lang3.ObjectUtils;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.OAuth2Provider;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.UserRole;

@Valid
@Builder
public record CreateUserRequestDto(
    @NotEmpty String email,
    @Nullable String password,
    OAuth2Provider provider,
    @Nullable String imageUrl,
    UserRole role) {

  public CreateUserRequestDto(
      String email, String password, OAuth2Provider provider, String imageUrl, UserRole role) {
    this.email = email;
    this.password = password;
    this.provider = ObjectUtils.defaultIfNull(provider, OAuth2Provider.LOCAL);
    this.imageUrl = imageUrl;
    this.role = ObjectUtils.defaultIfNull(role, UserRole.USER);
  }
}
