package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("jdbc")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    private UserDto userDto;
    private User user;
    private Long userId;

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

        userDto = UserDto.builder()
                .id(1L)
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .role(Role.ROLE_USER)
                .profileDto(ProfileDto.builder()
                        .id(1L)
                        .avatar("xxx")
                        .firstName("Serega")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build()).build();

        userId = userRepository.save(user).getId();
    }

    @Test
    @Order(1)
    @Sql(scripts = "classpath:/sql/deleteUser.sql", executionPhase = AFTER_TEST_METHOD)
    void findUserById() throws Exception {

        String responseAsString = mockMvc.perform(get("/api/v1/users/" + userId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserDto expectedUserDto = objectMapper.readValue(responseAsString, UserDto.class);
        assertEquals(expectedUserDto.getId(), userDto.getId());
        assertEquals(expectedUserDto.getUsername(), userDto.getUsername());
        assertEquals(expectedUserDto.getPassword(), userDto.getPassword());
        assertEquals(expectedUserDto.getRole(), userDto.getRole());
        assertEquals(expectedUserDto.getProfileDto(), userDto.getProfileDto());
    }

    @Test
    @Order(2)
    @Sql(scripts = "classpath:/sql/deleteUser.sql", executionPhase = AFTER_TEST_METHOD)
    void findAllUsers() throws Exception {
        String responseAsString = mockMvc.perform(get("/api/v1/users/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<UserDto> userDtoList = objectMapper.readValue(responseAsString, new TypeReference<>() {
        });
        assertTrue((userDtoList.size() > 0));
    }

    @Test
    @Order(3)
    @Sql(scripts = "classpath:/sql/deleteAll.sql", executionPhase = AFTER_TEST_METHOD)
    void deleteUserById() throws Exception {
        mockMvc.perform(delete("/api/v1/users/" + userId))
                .andExpect(status().is(200));
    }
}