package ro.kudostech.springreactsocialloginblueprint.modules.user.internal.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.UserService;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.User;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.entity.UserEntity;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.entity.UserRepository;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.service.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public List<User> getUsers() {
    return List.of();
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email).map(userMapper::toUser);
  }

  @Override
  public boolean hasUserWithEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  @Transactional
  public User saveUser(User user) {
    UserEntity userEntity = userMapper.toUserEntity(user);
    return userMapper.toUser(userRepository.save(userEntity));
  }

  @Override
  public void deleteUser(User user) {}
}
