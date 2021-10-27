package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("jpa")
@SpringBootTest
class UserMovieRepositoryJpaTest {
    @Autowired
    private UserMovieRepository userMovieRepository;
    @Autowired
    private UserRepository userRepository;

    private Long userMovieId;
    private UserMovie userMovie;
    private User user;


    @Test
    void save() {
        user = User.builder()
                .username("TestUserName")
                .password("TestPassword")
                .email("test1@mail.ru")
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar("xxx")
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build())
                .build();
        Profile profile = user.getProfile();
        profile.setUser(user);

        User savedUser = userRepository.save(user);

        userMovie = UserMovie.builder()
                .id(5L)
                .externalMovieId(999L)
                .notes("This is the best film !!!")
                .rating(8)
                .user(savedUser)
                .created(LocalDate.now())
                .build();
        userMovieId = userMovieRepository.save(userMovie).getId();
        assertNotNull(userMovieId);
        userMovieRepository.delete(userMovie);
    }

    @Test
    void findById() {
        user = User.builder()
                .username("TestUserName")
                .password("TestPassword")
                .email("test2@mail.ru")
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar("xxx")
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build())
                .build();
        Profile profile = user.getProfile();
        profile.setUser(user);

        User savedUser = userRepository.save(user);

        userMovie = UserMovie.builder()
                .id(5L)
                .externalMovieId(999L)
                .notes("This is the best film !!!")
                .rating(8)
                .user(savedUser)
                .created(LocalDate.now())
                .build();
        userMovieId = userMovieRepository.save(userMovie).getId();
        Optional<UserMovie> optionalUserMovie = userMovieRepository.findById(userMovieId);
        assertTrue(optionalUserMovie.isPresent());
        UserMovie userMovieGet = optionalUserMovie.get();
        assertEquals(userMovieId, userMovieGet.getId());
        userMovieRepository.delete(userMovie);
    }

    @Test
    void delete() {
        user = User.builder()
                .username("TestUserName")
                .password("TestPassword")
                .email("test3@mail.ru")
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar("xxx")
                        .firstName("Ivan")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build())
                .build();
        Profile profile = user.getProfile();
        profile.setUser(user);

        User savedUser = userRepository.save(user);

        userMovie = UserMovie.builder()
                .id(5L)
                .externalMovieId(999L)
                .notes("This is the best film !!!")
                .rating(8)
                .user(savedUser)
                .created(LocalDate.now())
                .build();
        userMovieId = userMovieRepository.save(userMovie).getId();

        userMovieRepository.delete(userMovie);
        assertEquals(Optional.empty(), userMovieRepository.findById(userMovie.getId()));
    }
}