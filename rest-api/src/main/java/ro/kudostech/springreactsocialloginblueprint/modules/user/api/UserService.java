package ro.kudostech.springreactsocialloginblueprint.modules.user.api;

import java.util.Optional;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.CreateUserRequestDto;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.UserDto;

public interface UserService {

  Optional<UserDto> getUserByEmail(String email);

  boolean hasUserWithEmail(String email);

  UserDto createUser(CreateUserRequestDto createUserRequest);
}