package ro.kudostech.springreactsocialloginblueprint.configuration.security;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.kudostech.springreactsocialloginblueprint.configuration.security.oauth2.CustomUserDetails;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.UserService;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UserDto userDto =
        userService
            .getUserByEmail(username)
            .orElseThrow(
                () ->
                    new UsernameNotFoundException(
                        String.format("Username %s not found", username)));
    List<SimpleGrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(userDto.role()));
    return mapUserToCustomUserDetails(userDto, authorities);
  }

  private CustomUserDetails mapUserToCustomUserDetails(
      UserDto userDto, List<SimpleGrantedAuthority> authorities) {
    CustomUserDetails customUserDetails = new CustomUserDetails();
    customUserDetails.setId(userDto.id());
    customUserDetails.setUsername(userDto.email());
    customUserDetails.setPassword(userDto.password());
    customUserDetails.setName(userDto.name());
    customUserDetails.setEmail(userDto.email());
    customUserDetails.setAuthorities(authorities);
    return customUserDetails;
  }
}
