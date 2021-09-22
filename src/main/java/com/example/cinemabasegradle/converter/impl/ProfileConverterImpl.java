package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.ProfileConverter;
import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.model.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverterImpl implements ProfileConverter {
    @Override
    public Profile toModel(ProfileDto profileDto) {
        if (profileDto == null) {
            return null;
        }
        Profile profile = new Profile();
        profile.setAvatar(profileDto.getAvatar());
        profile.setAbout(profileDto.getAbout());
        profile.setEmail(profileDto.getEmail());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setAge(profileDto.getAge());
        profile.setGender(profileDto.getGender());
        profile.setRegion(profileDto.getRegion());
        profile.setLanguage(profileDto.getLanguage());
        return profile;
    }

    @Override
    public ProfileDto toDto(Profile profile) {
        if (profile == null) {
            return null;
        }
        ProfileDto profileDto = new ProfileDto();
        profileDto.setAvatar(profile.getAvatar());
        profileDto.setAbout(profile.getAbout());
        profileDto.setEmail(profile.getEmail());
        profileDto.setFirstName(profile.getFirstName());
        profileDto.setLastName(profile.getLastName());
        profileDto.setAge(profile.getAge());
        profileDto.setGender(profile.getGender());
        profileDto.setRegion(profile.getRegion());
        profileDto.setLanguage(profile.getLanguage());
        return profileDto;
    }
}
