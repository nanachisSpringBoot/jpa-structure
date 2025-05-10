package com.example.demo.mapper;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a User entity to a UserResponse DTO
     *
     * @param user the user entity to map
     * @return the mapped UserResponse DTO
     */

    UserResponse userToUserResponse(User user);
}
