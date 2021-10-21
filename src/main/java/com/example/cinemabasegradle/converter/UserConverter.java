package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.User;

public interface UserConverter {
    User toModel(UserDto userDto);

    UserDto toDto(User user);
}
