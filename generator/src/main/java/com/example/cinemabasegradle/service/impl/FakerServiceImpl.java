package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.github.javafaker.Faker;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class FakerServiceImpl {

    private static final Integer MIN_CHAR = 8;
    private static final Integer MAX_CHAR = 16;
    private static final Integer MIN_AGE = 18;
    private static final Integer MAX_AGE = 90;
    private static final String EMAIL = "@mail.ru";
    private static final String LANGUAGE = "en";
    private Faker faker = new Faker();
    private User user;

    public User createFakeUser() {
        user = User.builder()
                .username(faker.name().fullName())
                .password(faker.lorem().characters(MIN_CHAR, MAX_CHAR))
                .email(faker.name().firstName() + "_" + faker.name().lastName()
                        + faker.lorem().characters(MIN_CHAR, MAX_CHAR) + EMAIL)
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar(faker.avatar().image())
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .age(faker.number().numberBetween(MIN_AGE, MAX_AGE))
                        .language(LANGUAGE)
                        .build())
                .build();
        Profile profile = user.getProfile();
        profile.setUser(user);
        return user;
    }
}
