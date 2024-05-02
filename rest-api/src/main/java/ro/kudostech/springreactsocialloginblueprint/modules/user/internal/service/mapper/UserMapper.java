package ro.kudostech.springreactsocialloginblueprint.modules.user.internal.service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import ro.kudostech.springreactsocialloginblueprint.modules.user.api.model.User;
import ro.kudostech.springreactsocialloginblueprint.modules.user.internal.entity.UserEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
  UserEntity toUserEntity(User user);

  User toUser(UserEntity userEntity);
}
