package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.dto.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserMovieService {
    Long addToFavouriteMovies(MovieDto movieDto);

    MovieDto fetchFavouriteMovieById(Long id);

    void updateFavouriteMovie(MovieDto movieDto, Long userMovieId);

    void deleteFavouriteMovie(Long id);

    List<MovieDto> fetchAllByUserId(Long userId);

    Long countFavouriteByUserId(Long userId);
}
