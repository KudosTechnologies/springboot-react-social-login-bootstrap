package ro.kudostech.springreactsocialloginblueprint.configuration.security.authendpont;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequest {

  @Email private String email;

  @NotBlank private String password;
}
