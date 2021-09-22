package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.User;

import java.util.List;

public interface UserConverter {
    User toModel(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDtoList(List<User> userList);
}
