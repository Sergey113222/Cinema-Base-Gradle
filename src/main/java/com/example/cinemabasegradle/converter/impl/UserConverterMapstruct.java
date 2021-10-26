package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.User;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UserConverterMapstruct {

    @Autowired
    protected ProfileConverterMapstruct profileConverterMapstruct;

    @Mapping(target = "profile", expression = "java(profileConverterMapstruct.toModel(userDto.getProfileDto()))")
    public abstract User toModel(UserDto userDto);

    @Mapping(target = "profileDto", expression = "java(profileConverterMapstruct.toDto(user.getProfile()))")
    public abstract UserDto toDto(User user);
}
