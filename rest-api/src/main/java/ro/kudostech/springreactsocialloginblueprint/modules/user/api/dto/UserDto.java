package ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.OAuth2Provider;

@Builder
public record UserDto(
    @NotNull Long id,
    @NotNull String password,
    String name,
    @NotNull String email,
    String imageUrl,
    @NotNull String role,
    OAuth2Provider provider) {}
