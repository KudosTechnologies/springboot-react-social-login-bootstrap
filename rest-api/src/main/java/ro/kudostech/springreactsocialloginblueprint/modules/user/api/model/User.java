package ro.kudostech.springreactsocialloginblueprint.modules.user.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record User(
    @NotNull Long id,
    @NotNull String password,
    String name,
    @NotNull String email,
    String imageUrl,
    @NotNull String role,
    OAuth2Provider provider) {}
