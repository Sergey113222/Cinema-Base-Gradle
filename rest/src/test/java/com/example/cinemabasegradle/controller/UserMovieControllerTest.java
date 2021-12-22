package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.service.impl.UserMovieServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** JUnit test example */

@ExtendWith(SpringExtension.class)
class UserMovieControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserMovieServiceImpl userMovieService;
    @InjectMocks
    private UserMovieController userMovieController;

    @Captor
    private ArgumentCaptor<Long> captorId;

    private MovieDto movieDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userMovieController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();
        objectMapper = new ObjectMapper();

        movieDto = new MovieDto();
        movieDto.setExternalMovieId(602L);
        movieDto.setPersonalRating(8);
        movieDto.setPersonalNotes("it is my favourite movie!!!!");

    }

    @Test
    void addToFavouriteMovie() throws Exception {
        mockMvc.perform(post("/favourite")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userMovieService).addToFavouriteMovies(any());
    }

    @Test
    void findFavouriteMovieById() throws Exception {
        when(userMovieService.fetchFavouriteMovieById(any())).thenReturn(movieDto);

        mockMvc.perform(get("/favourite/" + 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id", Matchers.is(602)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.personal_notes", Matchers.is(movieDto.getPersonalNotes())))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.personal_rating", Matchers.is(movieDto.getPersonalRating())))
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(userMovieService).fetchFavouriteMovieById(captorId.capture());
        assertEquals(1L, captorId.getValue());
    }

    @Test
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

        verify(userMovieService).updateFavouriteMovie(any(), any());
    }

    @Test
    void deleteFavouriteMovie() throws Exception {
        mockMvc.perform(delete("/favourite/" + 1L))
                .andExpect(status().is(204));

        verify(userMovieService).deleteFavouriteMovie(captorId.capture());
        assertEquals(1L, captorId.getValue());
    }
}