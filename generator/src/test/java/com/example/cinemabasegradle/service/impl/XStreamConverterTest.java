package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class XStreamConverterTest {

    private static final String FILE_PATH = "user2.xml";
    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .id(1L)
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

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    @Order(1)
    void marshal() {
        XStreamConverter xStreamConverter = new XStreamConverter();
        xStreamConverter.marshal(userList, FILE_PATH);
        File file = new File(FILE_PATH);
        assertTrue(file.exists());
    }

    @Test
    @Order(2)
    void unmarshal() {
        XStreamConverter xStreamConverter = new XStreamConverter();
        List<User> users = xStreamConverter.unmarshal(FILE_PATH);
        assertTrue(users.stream().count() > 0);
    }
}