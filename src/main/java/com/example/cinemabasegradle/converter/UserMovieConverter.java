package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;

public interface UserMovieConverter {
    UserMovie createUserMovie(User user, Movie movie, MovieDto movieDto);

    MovieDto convertUserMovieToMovieDto(UserMovie userMovie);
}
