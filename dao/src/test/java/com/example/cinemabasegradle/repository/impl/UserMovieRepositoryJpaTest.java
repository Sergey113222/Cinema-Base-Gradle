package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.config.EmbeddedTestConfig;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("testJpa")
@SpringBootTest(classes = EmbeddedTestConfig.class)
@EnableJpaRepositories
@Transactional
class UserMovieRepositoryJpaTest {
    @Autowired
    private UserMovieRepository userMovieRepository;
    @Autowired
    private UserRepository userRepository;

    private Long userMovieId;
    private UserMovie userMovie;
    private User user;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void save() {
        assertNotNull(userMovieRepository.save(userMovie).getId());
    }

    @Test
    void findById() {
        userMovieId = userMovieRepository.save(userMovie).getId();
        Optional<UserMovie> optionalUserMovie = userMovieRepository.findById(userMovieId);
        assertTrue(optionalUserMovie.isPresent());
        UserMovie userMovieGet = optionalUserMovie.get();
        assertEquals(userMovieId, userMovieGet.getId());
    }

    @Test
    void delete() {
        userMovieId = userMovieRepository.save(userMovie).getId();
        userMovieRepository.delete(userMovie);
        assertEquals(Optional.empty(), userMovieRepository.findById(userMovie.getId()));
    }
}