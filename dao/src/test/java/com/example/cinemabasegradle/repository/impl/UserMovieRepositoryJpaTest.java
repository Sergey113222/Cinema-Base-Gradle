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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
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
                .viewed(true)
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

    @Test
    void findAll() {
        userMovieRepository.save(userMovie);
        userMovieRepository.save(userMovie);

        Page<UserMovie> pageAll = userMovieRepository.findAll(PageRequest.of(0, 3));
        List<UserMovie> userMovieList = pageAll.getContent();
        assertTrue(userMovieList.size() > 1);
    }

    @Test
    void findByRatingAfterAndCreatedAfter() {
        userMovieRepository.save(userMovie);
        userMovieRepository.save(userMovie);
        userMovie.setRating(1);
        userMovieRepository.save(userMovie);

        Page<UserMovie> pageAll = userMovieRepository.findByRatingAfterAndCreatedAfter(7,
                LocalDate.of(2000, 1, 1), PageRequest.of(0, 3));
        List<UserMovie> userMovieList = pageAll.getContent();
        assertEquals(2, userMovieList.size());

        Page<UserMovie> pageAllConstraint = userMovieRepository.findByRatingAfterAndCreatedAfter(7,
                LocalDate.of(2000, 1, 1), PageRequest.of(0, 1));
        List<UserMovie> userMovieListConstraint = pageAllConstraint.getContent();
        assertEquals(1, userMovieListConstraint.size());
    }

    @Test
    void findByNotesContainingAndViewedTrue() {
        userMovieRepository.save(userMovie);
        userMovie.setViewed(false);
        userMovieRepository.save(userMovie);
        userMovie.setViewed(true);
        userMovie.setNotes("This is the worst film !!!");
        userMovieRepository.save(userMovie);

        Page<UserMovie> pageAll = userMovieRepository.findByNotesContainingAndViewedTrue("best", PageRequest.of(0, 3));
        List<UserMovie> userMovieList = pageAll.getContent();
        assertEquals(1, userMovieList.size());
    }
}