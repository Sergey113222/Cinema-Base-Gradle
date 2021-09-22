package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.model.Profile;

public interface ProfileConverter {
    Profile toModel(ProfileDto profileDto);

    ProfileDto toDto(Profile profile);
}
