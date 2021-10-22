package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import com.example.cinemabasegradle.exception.SearchMovieException;
import com.example.cinemabasegradle.service.SearchService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private static final String JSON_NODE_STR = "results";
    private static final String API_KEY = "api_key";
    private static final String QUERY = "query";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${themoviedb.ord.api-key}")
    private String apiKey;

    @Value("${themoviedb.ord.scheme}")
    private String scheme;

    @Value("${themoviedb.ord.host}")
    private String host;

    @Value("${themoviedb.ord.path-search-movie-by-name}")
    private String searchMovieByName;

    @Value("${themoviedb.ord.path-search-movie-popular}")
    private String searchMoviePopular;

    @Value("${themoviedb.ord.path-search-movie-latest}")
    private String searchMovieLatest;

    @Value("${themoviedb.ord.path-search-movie-by-id}")
    private String searchMovieById;

    @Override
    public MovieDto searchMoviesById(@PathVariable("movie_id") Long id) {

        URI uri = createURI(searchMovieById + id).build().toUri();
        return getMovieFromResource(uri);
    }

    @Override
    public List<MovieDto> searchMoviesByName(SearchDto dto) {
        URI uri = createURI(searchMovieByName).queryParam(QUERY, dto.getQuery()).build().toUri();
        return getMoviesListFromResource(uri);
    }

    @Override
    public List<MovieDto> searchMoviesPopular() {
        URI uri = createURI(searchMoviePopular).build().toUri();
        return getMoviesListFromResource(uri);
    }

    @Override
    public MovieDto searchMovieLatest() {
        URI uri = createURI(searchMovieLatest).build().toUri();
        return getMovieFromResource(uri);
    }

    private List<MovieDto> getMoviesListFromResource(URI uri) {
        try {
            RequestEntity request = new RequestEntity(HttpMethod.GET, uri);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode responseBody = objectMapper.readTree(response.getBody());
            JsonNode resultsMassive = responseBody.path(JSON_NODE_STR);
            return objectMapper.readValue(resultsMassive.toString(), new TypeReference<List<MovieDto>>() {
            });
        } catch (Exception e) {
            log.error(String.format("Can't get movie from resource. %s", e.getMessage()));
            throw new SearchMovieException("Can't get movie from resource");
        }
    }

    private MovieDto getMovieFromResource(URI uri) {
        try {
            RequestEntity request = new RequestEntity(HttpMethod.GET, uri);
            ResponseEntity<String> response = restTemplate.exchange(request, String.class);

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JsonNode responseBody = objectMapper.readTree(response.getBody());
            return objectMapper.readValue(responseBody.toString(), MovieDto.class);
        } catch (Exception e) {
            log.error(String.format("Can't get movie from resource. %s", e.getMessage()));
            throw new SearchMovieException("Can't get movie from resource");
        }
    }

    private UriComponentsBuilder createURI(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(path)
                .queryParam(API_KEY, apiKey);
    }
}
