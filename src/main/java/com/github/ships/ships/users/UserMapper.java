package com.github.ships.ships.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    UserGetDTO userToUserGetDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    User userPostDtoToUser(UserPostDTO userPostDTO);
}
