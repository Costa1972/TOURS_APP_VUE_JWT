package ru.costa.TOURS_APP_VUE_JWT.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.costa.TOURS_APP_VUE_JWT.dtos.UserDto;
import ru.costa.TOURS_APP_VUE_JWT.models.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserDto toUserDto(User user);
    User toUser(UserDto userDto);
    List<UserDto> toUserDtos(List<User> users);
}
