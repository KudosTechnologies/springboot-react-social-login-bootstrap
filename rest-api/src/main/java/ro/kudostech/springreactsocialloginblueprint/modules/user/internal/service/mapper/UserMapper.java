package ro.kudostech.springreactsocialloginblueprint.modules.user.internal.service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.dto.UserDto;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.domain.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
  User toUserEntity(UserDto userDto);

  UserDto toUser(User user);
}
