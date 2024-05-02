package ro.kudostech.springreactsocialloginblueprint.configuration.security.authendpont;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.kudostech.springreactsocialloginblueprint.configuration.security.TokenProvider;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.UserService;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.CreateUserRequestDto;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final UserService userService;
  private final AuthenticationManager authenticationManager;
  private final TokenProvider tokenProvider;

  @PostMapping("/authenticate")
  public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
    return new AuthResponse(token);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/signup")
  public AuthResponse signUp(@Valid @RequestBody CreateUserRequestDto signUpRequest) {
    if (userService.hasUserWithEmail(signUpRequest.email())) {
      throw new DuplicatedUserInfoException(
          String.format("Email %s already been used", signUpRequest.email()));
    }

    userService.createUser(signUpRequest);

    String token = authenticateAndGetToken(signUpRequest.email(), signUpRequest.password());
    return new AuthResponse(token);
  }

  private String authenticateAndGetToken(String username, String password) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password));
    return tokenProvider.generate(authentication);
  }
}
