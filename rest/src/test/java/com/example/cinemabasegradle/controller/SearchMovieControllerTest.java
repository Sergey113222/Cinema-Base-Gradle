package com.example.cinemabasegradle.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Integration test example */

@ActiveProfiles("testJdbc")
@SpringBootTest
@AutoConfigureMockMvc
class SearchMovieControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private SearchDto searchDto;

    {
        searchDto = new SearchDto();
        searchDto.setQuery("Matrix");
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void searchMoviesByName() throws Exception {
        String responseAsString = mockMvc
                .perform(post("/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(searchDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<MovieDto> movieDtoList = objectMapper.readValue(responseAsString, new TypeReference<>() {
        });
        assertTrue((movieDtoList.size() > 0));
    }


    @Test
    void searchMoviesPopular() throws Exception {
        String responseAsString = mockMvc.perform(get("/search/popular"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<MovieDto> movieDtoList = objectMapper.readValue(responseAsString, new TypeReference<>() {
        });
        assertTrue((movieDtoList.size() > 0));

    }

    @Test
    void searchMovieLatest() throws Exception {
        String responseAsString = mockMvc.perform(get("/search/latest"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        MovieDto movieDto = objectMapper.readValue(responseAsString, MovieDto.class);
        assertNotNull(movieDto.getTitle());
    }
}