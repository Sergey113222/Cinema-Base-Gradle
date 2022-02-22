package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemabasegradle.service.SearchService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/search")
public class SearchMovieController {

    private final SearchService searchService;

    @PostMapping
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public List<MovieDto> searchMoviesByName(@RequestBody @Valid SearchDto dto) {
        return searchService.searchMoviesByName(dto);
    }

    @GetMapping(value = "/popular")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public List<MovieDto> searchMoviesPopular() {
        return searchService.searchMoviesPopular();
    }

    @GetMapping(value = "/latest")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public MovieDto searchMovieLatest() {
        return searchService.searchMovieLatest();
    }
}
