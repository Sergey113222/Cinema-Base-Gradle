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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("testJdbc")
@SpringBootTest(classes = EmbeddedTestConfig.class)
@Transactional
class UserMovieRepositoryJdbcImplTest {

    @Autowired
    private UserMovieRepository userMovieRepository;
    @Autowired
    private UserRepository userRepository;

    private static final Long USER_MOVIE_ID = 99L;
    private UserMovie userMovie;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("TestUserName1")
                .password("TestPassword1")
                .email("testEmail1@mail.ru")
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar("xxx1")
                        .firstName("Ivan1")
                        .lastName("Ivanov1")
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
                .viewed(true)
                .created(LocalDate.now())
                .build();

    }

    @Test
    void save() {
        assertNotNull(userMovieRepository.save(userMovie).getId());
    }

    @Test
    @Sql(scripts = "classpath:/sql/userMovie.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void findById() {
        Optional<UserMovie> optionalUserMovie = userMovieRepository.findById(USER_MOVIE_ID);
        UserMovie userMovieGet = optionalUserMovie.get();
        assertEquals(USER_MOVIE_ID, userMovieGet.getId());
    }

    @Test
    void delete() {
        UserMovie savedUserMovie = userMovieRepository.save(userMovie);
        userMovieRepository.delete(savedUserMovie);
        assertFalse(userMovieRepository.findById(savedUserMovie.getId()).isPresent());
    }

    @Test
    void findAllByUserId() {
        Long userId = userMovieRepository.save(userMovie).getId();
        List<UserMovie> userMovieList = userMovieRepository.findAllByUserId(userId);
        assertTrue(userMovieList.size() > 0);
    }

    @Test
    void countUserMovieByUserId() {
        Long userId = userMovieRepository.save(userMovie).getId();
        assertEquals(1, userMovieRepository.countUserMovieByUserId(userId));
    }

    @Test
    void findByRatingAfterAndCreatedAfter() {
        userMovieRepository.save(userMovie);
        userMovie.setId(null);
        userMovieRepository.save(userMovie);
        userMovie.setRating(1);
        userMovie.setId(null);
        userMovieRepository.save(userMovie);

        Page<UserMovie> pageAll = userMovieRepository.findByRatingAfterAndCreatedAfter(1,
                LocalDate.of(2000, 1, 1), PageRequest.of(0, 3));
        List<UserMovie> userMovieList = pageAll.getContent();
        assertEquals(2, userMovieList.size());
    }

    @Test
    void findByNotesContainingAndViewedTrue() {
        userMovieRepository.save(userMovie);
        userMovie.setId(null);
        userMovie.setViewed(false);
        userMovieRepository.save(userMovie);
        userMovie.setId(null);
        userMovie.setViewed(true);
        userMovie.setNotes("This is the worst film !!!");
        userMovieRepository.save(userMovie);

        Page<UserMovie> pageAll = userMovieRepository.findByNotesContainingAndViewedTrue("best", PageRequest.of(0, 3));
        List<UserMovie> userMovieList = pageAll.getContent();
        assertEquals(1, userMovieList.size());
    }
}