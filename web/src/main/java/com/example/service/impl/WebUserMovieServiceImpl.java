package com.example.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.service.WebUserMovieService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class WebUserMovieServiceImpl implements WebUserMovieService {

    private static final String QUERY = "userMovieId";

    private final RestTemplate restTemplate;

    @Value("${cinema-base.scheme}")
    private String scheme;

    @Value("${cinema-base.host}")
    private String host;

    @Value("${cinema-base.port}")
    private Integer port;

    @Value("${cinema-base.favourite}")
    private String favourite;

    @Value("${cinema-base.favourite.all}")
    private String favouriteAll;

    @Override
    public void addToFavouriteMovies(MovieDto movieDto) {
        URI uri = createURI(favourite).build().toUri();
        restTemplate.postForObject(uri, movieDto, Long.class);
    }

    @Override
    public void updateFavouriteMovie(MovieDto movieDto, Long userMovieId) {
        URI uri = createURI(favourite).queryParam(QUERY, userMovieId).build().toUri();
        restTemplate.put(uri, movieDto);
    }

    @Override
    public void deleteFavouriteMovie(Long id) {
        URI uri = createURI(favourite + id).build().toUri();
        restTemplate.delete(uri);
    }

    @Override
    public List<MovieDto> fetchAllByUserId(@PathVariable("user_id") Long id) {
        URI uri = createURI(favouriteAll + id).build().toUri();
        MovieDto[] request = restTemplate.getForObject(uri, MovieDto[].class);
        assert request != null;
        return Arrays.asList(request);
    }

    @Override
    public MovieDto fetchFavouriteMovieByUserId(@PathVariable("user_id") Long id) {
        URI uri = createURI(favourite + id).build().toUri();
        return restTemplate.getForObject(uri, MovieDto.class);
    }

    private UriComponentsBuilder createURI(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path);
    }
}
