package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.ProfileConverter;
import com.example.cinemabasegradle.converter.UserConverter;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.User;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConverterImpl implements UserConverter {

    private final ProfileConverter profileConverter;

    @Override
    public User toModel(UserDto userDto) {
        if (ObjectUtils.anyNull(userDto, userDto.getUsername(), userDto.getPassword())) {
            throw new IllegalArgumentException("Some of required fields is null: " + userDto);
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
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
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole());
        userDto.setProfileDto(profileConverter.toDto(user.getProfile()));
        return userDto;
    }

    @Override
    public List<UserDto> toDtoList(List<User> userList) {
        return userList.stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setPassword(user.getPassword());
            userDto.setRole(user.getRole());
            userDto.setProfileDto(profileConverter.toDto(user.getProfile()));
            return userDto;
        }).collect(Collectors.toList());
    }
}
