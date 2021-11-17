package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.model.Profile;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileMapper {
    Profile toModel(ProfileDto profileDto);

    ProfileDto toDto(Profile profile);

}
