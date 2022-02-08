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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = EmbeddedTestConfig.class)
@ActiveProfiles("testJdbc")
@Transactional
class UserRepositoryJdbcImplTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp() {
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        roleList.add(role);

        user = User.builder()
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .roles(roleList)
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
    void findByIdAndActiveTrue() {
        User savedUser = userRepository.save(user);
        Optional<User> optionalUser = userRepository.findByIdAndActiveTrue(savedUser.getId());
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(savedUser.getId(), user.getId());
        assertTrue(user.getActive());
    }

    @Test
    void findByUsername() {
        User savedUser = userRepository.save(user);
        User user = userRepository.findByUsername(savedUser.getUsername());
        assertEquals(savedUser.getUsername(), user.getUsername());
    }

    @Test
    void findAll() {
        userRepository.save(user);
        List<User> userList = userRepository.findAll();
        assertEquals(1, userList.size());
    }

    @Test
    void findByEmailAndActiveTrue() {
        User savedUser = userRepository.save(user);
        Optional<User> optionalUser = userRepository.findByEmailAndActiveTrue(savedUser.getEmail());
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(savedUser.getEmail(), user.getEmail());
        assertTrue(user.getActive());
    }

    @Test
    void save() {
        assertNotNull(userRepository.save(user).getId());
    }

    @Test
    void deleteUser() {
        Long id = userRepository.save(user).getId();
        userRepository.deleteUser(id);
        User deletedUser = userRepository.findByUsername(user.getUsername());
        assertNotNull(deletedUser.getId());
        assertFalse(deletedUser.getActive());
    }
}