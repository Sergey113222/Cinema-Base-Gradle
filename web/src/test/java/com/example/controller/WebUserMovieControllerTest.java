package com.example.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.service.WebUserMovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class WebUserMovieControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private WebUserMovieService webUserMovieService;
    @InjectMocks
    private WebUserMovieController webUserMovieController;

    @Captor
    private ArgumentCaptor<Long> captorId;

    private MovieDto movieDto;
    private List<MovieDto> movieDtoList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(webUserMovieController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();
        objectMapper = new ObjectMapper();

        movieDto = new MovieDto();
        movieDto.setExternalMovieId(602L);
        movieDto.setPersonalRating(8);
        movieDto.setPersonalNotes("it is my favourite movie!!!!");

        movieDtoList = new ArrayList<>();
        movieDtoList.add(movieDto);

    }

    @Test
    void fetchAllByUserId() throws Exception {
        when(webUserMovieService.fetchAllByUserId(any())).thenReturn(movieDtoList);

        mockMvc.perform(get("/favourite/" + 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(webUserMovieService).fetchAllByUserId(captorId.capture());
        assertEquals(1L, captorId.getValue());
    }

    @Test
    void viewAddMoviePage() throws Exception {
        mockMvc.perform(get("/movie/add/" + 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void addFavouriteMovie() throws Exception {
        mockMvc.perform(post("/favourite/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().is(302))
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(webUserMovieService).addToFavouriteMovies(any());
    }

    @Test
    void getFavouriteMovie() throws Exception {
        when(webUserMovieService.fetchFavouriteMovieByUserId(any())).thenReturn(movieDto);

        mockMvc.perform(get("/favourite/edit/" + 1L))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(webUserMovieService).fetchFavouriteMovieByUserId(captorId.capture());
        assertEquals(1L, captorId.getValue());
    }

    @Test
    void deleteFavouriteMovie() throws Exception {
        mockMvc.perform(post("/favourite/delete/" + 1L))
                .andExpect(status().is(302));

        verify(webUserMovieService).deleteFavouriteMovie(captorId.capture());
        assertEquals(1L, captorId.getValue());
    }

    @Test
    void editFavouriteMovie() throws Exception {
        movieDto.setPersonalRating(1);
        mockMvc.perform(post("/favourite/edit/" + 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(movieDto)))
                .andExpect(status().is(302))
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(webUserMovieService).updateFavouriteMovie(any(), any());
    }
}