package ro.kudostech.springreactsocialloginblueprint.modules.user.api;

import java.util.List;
import java.util.Optional;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.User;

public interface UserService {
  List<User> getUsers();

  Optional<User> getUserByEmail(String email);

  boolean hasUserWithEmail(String email);

  User saveUser(User user);

  void deleteUser(User user);
}
