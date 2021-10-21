package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.ProfileConverter;
import com.example.cinemabasegradle.converter.UserConverter;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverterImpl implements UserConverter {

    private final ProfileConverter profileConverter;

    @Override
    public User toModel(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setProfile(profileConverter.toModel(userDto.getProfileDto()));
        return user;
    }

    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setProfileDto(profileConverter.toDto(user.getProfile()));
        return userDto;
    }
}
