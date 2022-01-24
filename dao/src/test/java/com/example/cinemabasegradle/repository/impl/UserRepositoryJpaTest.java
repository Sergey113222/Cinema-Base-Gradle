package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.config.EmbeddedTestConfig;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = EmbeddedTestConfig.class)
@ActiveProfiles("testJpa")
@EnableJpaRepositories
class UserRepositoryJpaTest {

    @Autowired
    private UserRepository userRepository;
    private User user;
    private static final Long ID = 10L;
    private static final String USERNAME = "TestUsername";
    private static final String EMAIL = "test@mail.ru";

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
    }


    @Test
    @Transactional
    void findByIdAndActiveTrue() {
        User savedUser = userRepository.save(user);
        Optional<User> optionalUser = userRepository.findByIdAndActiveTrue(savedUser.getId());
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(savedUser.getId(), user.getId());
        assertTrue(user.getActive());
    }

    @Test
    @Transactional
    void findByUsername() {
        User savedUser = userRepository.save(user);
        User user = userRepository.findByUsername(savedUser.getUsername());
        assertEquals(savedUser.getUsername(), user.getUsername());
    }

    @Test
    @Transactional
    void findAll() {
        userRepository.save(user);
        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
    }

    @Test
    @Transactional
    void findByEmailAndActiveTrue() {
        User savedUser = userRepository.save(user);
        Optional<User> optionalUser = userRepository.findByEmailAndActiveTrue(savedUser.getEmail());
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertTrue(user.getActive());
    }

    @Test
    @Transactional
    void save() {
        assertNotNull(userRepository.save(user).getId());
    }

    @Test
    void deleteUser() {
        user.setEmail("test2@mail.ru");
        Long id = userRepository.save(user).getId();
        userRepository.deleteUser(id);
        User deletedUser = userRepository.findByUsername(user.getUsername());
        assertNotNull(deletedUser.getId());
        assertFalse(deletedUser.getActive());
    }
}