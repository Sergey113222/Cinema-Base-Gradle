package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "profile", source = "profileDto")
    User toModel(UserDto userDto);

    @Mapping(target = "profileDto", source = "profile")
    UserDto toDto(User user);
}
