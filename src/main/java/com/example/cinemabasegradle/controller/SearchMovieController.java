package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import com.example.cinemabasegradle.service.SearchService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/search")
public class SearchMovieController {

    private final SearchService searchService;

    @PostMapping
    @ApiOperation(value = "Find movies by name from https://www.themoviedb.org")
    public List<MovieDto> searchMoviesByName(@RequestBody @Valid SearchDto dto) {
        return searchService.searchMoviesByName(dto);
    }

    @GetMapping(value = "/popular")
    @ApiOperation(value = "Find popular movies from https://www.themoviedb.org")
    public List<MovieDto> searchMoviesPopular() {
        return searchService.searchMoviesPopular();
    }

    @GetMapping(value = "/latest")
    @ApiOperation(value = "Find lates movie from https://www.themoviedb.org")
    public MovieDto searchMovieLatest() {
        return searchService.searchMovieLatest();
    }
}
