package com.example.service;

import com.example.cinemabasegradle.dto.MovieDto;

import java.util.List;

public interface WebUserMovieService {
    void addToFavouriteMovies(MovieDto movieDto);

    void updateFavouriteMovie(MovieDto movieDto, Long userMovieId);

    void deleteFavouriteMovie(Long id);

    List<MovieDto> fetchAllByUserId(Long userId);

    MovieDto fetchFavouriteMovieByUserId(Long userId);

}
