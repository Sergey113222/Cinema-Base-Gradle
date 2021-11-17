package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.ListResultsMovieDto;
import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import com.example.cinemabasegradle.service.SearchService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public class SearchServiceImpl implements SearchService {

    private static final String API_KEY = "api_key";
    private static final String QUERY = "query";
    private final RestTemplate restTemplate;

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
        ListResultsMovieDto request = restTemplate.getForObject(uri, ListResultsMovieDto.class);
        return Objects.requireNonNull(request).getResults();
    }

    private MovieDto getMovieFromResource(URI uri) {
        return restTemplate.getForObject(uri, MovieDto.class);
    }

    private UriComponentsBuilder createURI(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(path)
                .queryParam(API_KEY, apiKey);
    }
}
