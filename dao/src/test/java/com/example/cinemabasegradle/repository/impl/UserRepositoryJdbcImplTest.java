package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.config.EmbeddedTestConfig;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EmbeddedTestConfig.class)
@ActiveProfiles("testJdbc")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = "classpath:/sql/user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/sql/deleteUser.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserRepositoryJdbcImplTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    private static final Long ID = 10L;
    private static final String USERNAME = "TestUsername";
    private static final String EMAIL = "test@mail.ru";


    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar("xxx")
                        .firstName("Serega")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build())
                .build();
        Profile profile = user.getProfile();
        profile.setUser(user);
    }

    @Test
    @Order(1)
    void findByIdAndActiveTrue() {
        Optional<User> optionalUser = userRepository.findByIdAndActiveTrue(ID);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(ID, user.getId());
        assertTrue(user.getActive());
    }

    @Test
    @Order(2)
    void findByUsername() {
        User user = userRepository.findByUsername(USERNAME);
        assertEquals(USERNAME, user.getUsername());
    }

    @Test
    @Order(3)
    void findAll() {
        userRepository.save(user);
        List<User> userList = userRepository.findAll();
        assertEquals(2, userList.size());
    }

    @Test
    @Order(4)
    void findByEmailAndActiveTrue() {
        Optional<User> optionalUser = userRepository.findByEmailAndActiveTrue(EMAIL);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(EMAIL, user.getEmail());
        assertTrue(user.getActive());
    }

    @Test
    @Order(5)
    void save() {
        assertNotNull(userRepository.save(user).getId());
    }

    @Test
    @Order(6)
    @Sql(scripts = "classpath:/sql/deleteAll.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void deleteUser() {
        Long id = userRepository.save(user).getId();
        userRepository.deleteUser(id);
        User deletedUser = userRepository.findByUsername(user.getUsername());
        assertNotNull(deletedUser.getId());
        assertFalse(deletedUser.getActive());
    }
}