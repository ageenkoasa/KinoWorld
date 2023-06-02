package com.example.kino.mapper;

import com.example.kino.dto.UserDto;
import com.example.kino.model.User;

public class UserMapper {
    private UserMapper() {}
    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());
    }
}
