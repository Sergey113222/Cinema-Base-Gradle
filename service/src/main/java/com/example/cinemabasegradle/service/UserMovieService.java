package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.dto.MovieDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UserMovieService {
    Long addToFavouriteMovies(MovieDto movieDto);

    MovieDto fetchFavouriteMovieById(Long id);

    void updateFavouriteMovie(MovieDto movieDto, Long userMovieId);

    void deleteFavouriteMovie(Long id);

    List<MovieDto> fetchAllByUserId(Long userId);

    Long countFavouriteByUserId(Long userId);

    List<MovieDto> sortByColumnNameAsc(Pageable pageable);

    List<MovieDto> filterByRatingAfterAndCreatedAfter(Integer rating, LocalDate created, Pageable pageable);

    List<MovieDto> filterByNotesContainingAndViewedTrue(String search, Pageable pageable);
}
