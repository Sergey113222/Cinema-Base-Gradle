package com.example.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebUserMovieServiceImplTest {

    private RestTemplate restTemplate;
    private WebUserMovieServiceImpl webUserMovieService;
    private MovieDto movieDto;
    private List<MovieDto> movieDtos;
    private URI uri;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        webUserMovieService = new WebUserMovieServiceImpl(restTemplate);
        ReflectionTestUtils.setField(webUserMovieService, "scheme", "http");
        ReflectionTestUtils.setField(webUserMovieService, "host", "localhost");
        ReflectionTestUtils.setField(webUserMovieService, "port", 8080);
        ReflectionTestUtils.setField(webUserMovieService, "favourite", "/favourite/");
        ReflectionTestUtils.setField(webUserMovieService, "favouriteAll", "/favourite/all/");

        movieDto = new MovieDto();
        movieDto.setExternalMovieId(602L);
        movieDto.setPersonalRating(8);
        movieDto.setPersonalNotes("it is my favourite movie!!!!");

        movieDtos = new ArrayList<>();
        movieDtos.add(movieDto);

    }

    @Test
    void addToFavouriteMovies() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/favourite/").build().toUri();
        webUserMovieService.addToFavouriteMovies(movieDto);
        verify(restTemplate).postForObject(uri, movieDto, Long.class);
    }

    @Test
    void updateFavouriteMovie() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/favourite/").queryParam("userMovieId", 1L).build().toUri();
        webUserMovieService.updateFavouriteMovie(movieDto, 1L);
        verify(restTemplate).put(uri, movieDto);
    }

    @Test
    void deleteFavouriteMovie() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/favourite/1").build().toUri();
        webUserMovieService.deleteFavouriteMovie(1L);
        verify(restTemplate).delete(uri);
    }

    @Test
    void fetchAllByUserId() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/favourite/all/1").build().toUri();

        ResponseEntity<List<MovieDto>> responseEntity = new ResponseEntity<>(movieDtos, HttpStatus.OK);
        when(restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieDto>>() {
        })).thenReturn(responseEntity);
        List<MovieDto> movieDtoListFound = webUserMovieService.fetchAllByUserId(1L);
        verify(restTemplate).exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieDto>>() {
        });
        assertTrue(movieDtoListFound.size() > 0);
    }

    @Test
    void fetchFavouriteMovieByUserId() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/favourite/1").build().toUri();
        when(restTemplate.getForObject(uri, MovieDto.class)).thenReturn(movieDto);

        MovieDto movieDtoFound = webUserMovieService.fetchFavouriteMovieByUserId(1L);
        verify(restTemplate).getForObject(uri, MovieDto.class);
        assertEquals(movieDto.getPersonalRating(), movieDtoFound.getPersonalRating());
    }
}