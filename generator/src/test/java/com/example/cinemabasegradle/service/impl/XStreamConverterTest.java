package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class XStreamConverterTest {

    private static final String FILE_PATH_READ = "src/test/resources/user2.xml";
    private static final String FILE_PATH_WRITE = "src/test/resources/user1.xml";
    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        roleList.add(role);

        user = User.builder()
                .id(1L)
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

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    void marshal() {
        XStreamConverter xStreamConverter = new XStreamConverter();
        xStreamConverter.marshal(userList, FILE_PATH_WRITE);
        File file = new File(FILE_PATH_WRITE);
        assertTrue(file.exists());
    }

    @Test
    void unmarshal() {
        XStreamConverter xStreamConverter = new XStreamConverter();
        List<User> users = xStreamConverter.unmarshal(FILE_PATH_READ);
        assertTrue(users.size() > 0);
    }

    @AfterAll
    public static void clean() {
        File file = new File(FILE_PATH_WRITE);
        boolean success = file.delete();
        assertTrue(success);
    }
}