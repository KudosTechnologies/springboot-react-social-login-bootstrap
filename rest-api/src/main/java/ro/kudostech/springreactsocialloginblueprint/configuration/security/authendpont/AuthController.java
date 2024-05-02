package ro.kudostech.springreactsocialloginblueprint.configuration.security.authendpont;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.kudostech.springreactsocialloginblueprint.configuration.security.TokenProvider;
import ro.kudostech.springreactsocialloginblueprint.configuration.security.WebSecurityConfig;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.UserService;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.User;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  @PostMapping("/authenticate")
  public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
    return new AuthResponse(token);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/signup")
  public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
      throw new DuplicatedUserInfoException(
          String.format("Email %s already been used", signUpRequest.getEmail()));
    }

    userService.saveUser(mapSignUpRequestToUser(signUpRequest));

    String token = authenticateAndGetToken(signUpRequest.getEmail(), signUpRequest.getPassword());
    return new AuthResponse(token);
  }

  private String authenticateAndGetToken(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
    return tokenProvider.generate(authentication);
  }

  private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
    return User.builder()
        .email(signUpRequest.getEmail())
        .password(passwordEncoder.encode(signUpRequest.getPassword()))
        .role(WebSecurityConfig.USER)
        .build();
  }
}
