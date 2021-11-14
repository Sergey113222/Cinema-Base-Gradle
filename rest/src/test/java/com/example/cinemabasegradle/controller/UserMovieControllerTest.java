package com.example.cinemabasegradle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
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
import com.example.cinemabasegradle.repository.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("testJdbc")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    private final UserMovie userMovie;
    private final User user;
    private final MovieDto movieDto;

    {
        movieDto = new MovieDto();
        movieDto.setExternalMovieId(602L);
        movieDto.setPersonalRating(8);
        movieDto.setPersonalNotes("it is my favourite movie!!!!");
    }

    {
        user = User.builder()
                .username("Sergey")
                .password("G113222")
                .email("sergey@mail.ru")
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

        userMovie = UserMovie.builder()

                .externalMovieId(602L)
                .notes("it is my favourite movie!!!!")
                .rating(8)
                .user(user)
                .created(LocalDate.now())
                .build();
    }

    @Test
    @Order(1)
    void addToFavouriteMovie() throws Exception {
        userRepository.save(user);
        mockMvc.perform(post("/favourite")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(2)
    void findFavouriteMovieById() throws Exception {
        String responseAsString = mockMvc
                .perform(get("/favourite/" + 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        MovieDto movieDto = objectMapper.readValue(responseAsString, MovieDto.class);
        assertEquals(userMovie.getExternalMovieId(), movieDto.getExternalMovieId());
        assertEquals(userMovie.getNotes(), movieDto.getPersonalNotes());
        assertEquals(userMovie.getRating(), movieDto.getPersonalRating());
    }

    @Test
    @Order(3)
    void updateFavouriteMovie() throws Exception {
        movieDto.setPersonalRating(1);
        mockMvc.perform(put("/favourite")
                        .param("userMovieId", String.valueOf(1L))
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @Order(4)
    @Sql(scripts = "classpath:/sql/deleteAll.sql", executionPhase = AFTER_TEST_METHOD)
    void deleteFavouriteMovie() throws Exception {
        mockMvc.perform(delete("/favourite/" + 1L))
                .andExpect(status().is(204));
    }
}