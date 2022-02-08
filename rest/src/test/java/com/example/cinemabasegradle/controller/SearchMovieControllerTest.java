package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.SearchDto;
import com.example.cinemabasegradle.service.impl.SearchServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class SearchMovieControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private SearchServiceImpl searchService;
    @InjectMocks
    private SearchMovieController searchMovieController;

    private SearchDto searchDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchMovieController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();
        objectMapper = new ObjectMapper();

        searchDto = new SearchDto();
        searchDto.setQuery("Matrix");
    }

    @Test
    void searchMoviesByName() throws Exception {
        mockMvc.perform(post("/search")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(searchDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(searchService).searchMoviesByName(any());
    }

    @Test
    void searchMoviesPopular() throws Exception {
        mockMvc.perform(get("/search/popular"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(searchService).searchMoviesPopular();
    }

    @Test
    void searchMovieLatest() throws Exception {
        mockMvc.perform(get("/search/latest"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(searchService).searchMovieLatest();
    }
}