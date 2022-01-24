package com.example.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebSearchServiceImplTest {

    private RestTemplate restTemplate;
    private WebSearchServiceImpl webSearchService;
    private List<MovieDto> movieDtos;
    private URI uri;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        webSearchService = new WebSearchServiceImpl(restTemplate);
        ReflectionTestUtils.setField(webSearchService, "scheme", "http");
        ReflectionTestUtils.setField(webSearchService, "host", "localhost");
        ReflectionTestUtils.setField(webSearchService, "port", 8080);
        ReflectionTestUtils.setField(webSearchService, "searchMoviePopular", "/search/popular");

        MovieDto movieDto = new MovieDto();
        movieDto.setExternalMovieId(602L);
        movieDto.setPersonalRating(8);
        movieDto.setPersonalNotes("it is my favourite movie!!!!");

        movieDtos = new ArrayList<>();
        movieDtos.add(movieDto);

        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/search/popular").build().toUri();
    }

    @Test
    void getMovieListPopular() {
        ResponseEntity<List<MovieDto>> responseEntity = new ResponseEntity<>(movieDtos, HttpStatus.OK);
        when(restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieDto>>() {
        })).thenReturn(responseEntity);
        List<MovieDto> movieDtoListFound = webSearchService.getPopularMovies();
        verify(restTemplate, Mockito.times(1))
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<MovieDto>>() {
        });
        assertTrue(movieDtoListFound.size() > 0);
    }
}