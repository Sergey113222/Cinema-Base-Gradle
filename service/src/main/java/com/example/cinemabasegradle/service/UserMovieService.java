package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.dto.MovieDto;
import org.springframework.stereotype.Service;

@Service
public interface UserMovieService {
    Long addToFavouriteMovies(MovieDto movieDto);

    MovieDto fetchFavouriteMovieById(Long id);

    void updateFavouriteMovie(MovieDto movieDto, Long userMovieId);

    void deleteFavouriteMovie(Long id);
}
