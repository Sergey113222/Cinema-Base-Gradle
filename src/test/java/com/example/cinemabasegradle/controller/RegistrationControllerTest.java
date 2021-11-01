package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("jdbc")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
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
    void register() throws Exception {

        String responseAsString = mockMvc
                .perform(post("/registration/new")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(2)
    @Sql(scripts = "classpath:/sql/deleteAll.sql", executionPhase = AFTER_TEST_METHOD)
    void updateUser() throws Exception {
        userDto.setEmail("update@mail.ru");
        userDto.setId(userId);
        String responseAsString = mockMvc
                .perform(put("/registration/update")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}