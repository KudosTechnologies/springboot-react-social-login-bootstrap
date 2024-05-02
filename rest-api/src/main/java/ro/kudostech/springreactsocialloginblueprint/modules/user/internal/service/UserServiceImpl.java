package ro.kudostech.springreactsocialloginblueprint.modules.user.internal.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.UserService;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.CreateUserRequestDto;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.UserDto;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.User;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.UserRepository;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Optional<UserDto> getUserByEmail(String email) {
    return userRepository.findByEmail(email).map(userMapper::toUser);
  }

  @Override
  public boolean hasUserWithEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  @Transactional
  public UserDto createUser(CreateUserRequestDto createUserRequest) {
    User user =
        User.builder()
            .name(createUserRequest.email())
            .email(createUserRequest.email())
            .password(passwordEncoder.encode(createUserRequest.password()))
            .role(createUserRequest.role())
            .provider(createUserRequest.provider())
            .imageUrl(createUserRequest.imageUrl())
            .build();
    return userMapper.toUser(userRepository.save(user));
  }
}
