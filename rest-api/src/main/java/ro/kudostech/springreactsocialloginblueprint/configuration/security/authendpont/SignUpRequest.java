package ro.kudostech.springreactsocialloginblueprint.configuration.security.authendpont;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

  @Schema(example = "user3@mycompany.com")
  @Email
  private String email;

  @Schema(example = "user3")
  @NotBlank
  private String password;
}
