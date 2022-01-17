package com.example.service;

import com.example.cinemabasegradle.dto.MovieDto;

import java.util.List;

public interface WebSearchService {
    List<MovieDto> searchMoviesPopular();
}
