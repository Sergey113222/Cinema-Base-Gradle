package com.example.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.service.WebSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSearchServiceImpl implements WebSearchService {

    private final RestTemplate restTemplate;

    @Value("${cinema-base.scheme}")
    private String scheme;

    @Value("${cinema-base.host}")
    private String host;

    @Value("${cinema-base.path-search-movie-popular}")
    private String searchMoviePopular;

    @Override
    public List<MovieDto> searchMoviesPopular() {
        URI uri = createURI(searchMoviePopular).build().toUri();
        return getMoviesListFromResource(uri);
    }

    private List<MovieDto> getMoviesListFromResource(URI uri) {

        MovieDto[] request = restTemplate.getForObject(uri, MovieDto[].class);
        return Arrays.asList(request);
    }

    private UriComponentsBuilder createURI(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(8080)
                .path(path);
    }
}
