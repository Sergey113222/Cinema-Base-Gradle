package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.config.EmbeddedTestConfig;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("testJdbc")
@SpringBootTest(classes = EmbeddedTestConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMovieRepositoryJdbcImplTest {

    @Autowired
    private UserMovieRepository userMovieRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Long USER_MOVIE_ID = 99L;
    private UserMovie userMovie;
    private User user;

    @Test
    @Order(1)
    void save() {
        user = User.builder()
                .username("TestUserName")
                .password("TestPassword")
                .email("testEmail@mail.ru")
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

                .externalMovieId(999L)
                .notes("This is the best film !!!")
                .rating(8)
                .user(savedUser)
                .created(LocalDate.now())
                .build();
        assertNotNull(userMovieRepository.save(userMovie).getId());
    }

    @Test
    @Order(2)
    @Sql(scripts = "classpath:/sql/userMovie.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findById() {
        Optional<UserMovie> optionalUserMovie = userMovieRepository.findById(USER_MOVIE_ID);
        UserMovie userMovieGet = optionalUserMovie.get();
        assertEquals(USER_MOVIE_ID, userMovieGet.getId());
    }

    @Test
    @Order(3)
    @Sql(scripts = "classpath:/sql/deleteAll.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void delete() {
        user = User.builder()
                .username("Test2UserName")
                .password("Test2Password")
                .email("test2Email@mail.ru")
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

                .externalMovieId(602L)
                .notes("This is the best film !!!")
                .rating(8)
                .user(savedUser)
                .created(LocalDate.now())
                .build();
        UserMovie savedUserMovie = userMovieRepository.save(userMovie);
        userMovieRepository.delete(savedUserMovie);
        assertFalse(userMovieRepository.findById(savedUserMovie.getId()).isPresent());
    }
}