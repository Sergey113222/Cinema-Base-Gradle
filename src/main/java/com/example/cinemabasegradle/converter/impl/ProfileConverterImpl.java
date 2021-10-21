package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.ProfileConverter;
import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.model.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ProfileConverterImpl implements ProfileConverter {

    @Override
    public Profile toModel(ProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setId(profileDto.getId());
        profile.setAvatar(profileDto.getAvatar());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setAge(profileDto.getAge());
        profile.setLanguage(profileDto.getLanguage());
        profile.setCreated(LocalDate.now());
        return profile;
    }

    @Override
    public ProfileDto toDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        ProfileDto profileDto = new ProfileDto();
        profileDto.setId(profile.getId());
        profileDto.setAvatar(profile.getAvatar());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setAge(profile.getAge());
        profileDto.setLanguage(profile.getLanguage());
        return profileDto;
    }
}