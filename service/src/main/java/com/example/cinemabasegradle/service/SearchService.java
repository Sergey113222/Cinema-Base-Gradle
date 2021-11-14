package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.SearchDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface SearchService {

    List<MovieDto> searchMoviesByName(SearchDto dto);

    List<MovieDto> searchMoviesPopular();

    MovieDto searchMovieLatest();

    MovieDto searchMoviesById(@PathVariable("movie_id") Long id);
}
