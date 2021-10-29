package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import com.example.cinemabasegradle.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("jdbc")
@SpringBootTest
class SearchServiceImplTest {

    @Autowired
    private RestTemplate restTemplate;
    private SearchService searchService;
    private SearchDto searchDto;

    {
        searchDto = new SearchDto();
        searchDto.setQuery("Matrix");
    }

    @BeforeEach
    void setUp() {
        searchService = new SearchServiceImpl(restTemplate);
        ReflectionTestUtils.setField(searchService, "apiKey", "0b31fa0abdf8b9e72d279c0646c5bd08");
        ReflectionTestUtils.setField(searchService, "scheme", "https");
        ReflectionTestUtils.setField(searchService, "host", "api.themoviedb.org");
        ReflectionTestUtils.setField(searchService, "searchMovieByName", "/3/search/movie");
        ReflectionTestUtils.setField(searchService, "searchMoviePopular", "/3/movie/popular");
        ReflectionTestUtils.setField(searchService, "searchMovieLatest", "/3/movie/latest");
        ReflectionTestUtils.setField(searchService, "searchMovieById", "/3/movie/");
    }

    @Test
    void searchMoviesById() {
        assertNotNull(searchService.searchMoviesById(603L));
    }

    @Test
    void searchMoviesByName() {
        List<MovieDto> apiDtoList = searchService.searchMoviesByName(searchDto);
        assertTrue(apiDtoList.stream().count() > 0);
    }

    @Test
    void searchMoviesPopular() {
        searchService.searchMoviesPopular();
    }

    @Test
    void searchMovieLatest() {
        searchService.searchMovieLatest();
    }
}