package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.UserMovieConverter;
import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.model.Genre;
import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMovieConverterImpl implements UserMovieConverter {

    @Override
    public UserMovie createUserMovie(User user, Movie movie, MovieDto movieDto) {
        if ((user == null) || (movie == null) || (movieDto == null)) {
            return null;
        }
        UserMovie userMovie = new UserMovie();
        userMovie.setUser(user);
        userMovie.setMovie(movie);
        userMovie.setRating(movieDto.getPersonalRating());
        userMovie.setNotes(movieDto.getPersonalNotes());
        return userMovie;
    }

    @Override
    public MovieDto convertUserMovieToMovieDto(UserMovie userMovie) {
        if (userMovie == null) {
            return null;
        }
        MovieDto movieDto = new MovieDto();
        movieDto.setExternalMovieId(userMovie.getMovie().getExternalId());
        movieDto.setTitle(userMovie.getMovie().getTitle());
        movieDto.setPosterPath(userMovie.getMovie().getPoster());
        movieDto.setReleaseDate(userMovie.getMovie().getPremierDate());
        movieDto.setVoteAverage(userMovie.getMovie().getImdb());
        movieDto.setOverview(userMovie.getMovie().getDescription());
        movieDto.setAdult(userMovie.getMovie().getAdult());

        List<Long> genresIds = userMovie.getMovie().getGenres().stream().map(Genre::getExternalId).collect(Collectors.toList());
        movieDto.setGenreIds(genresIds);

        movieDto.setPersonalRating(userMovie.getRating());
        movieDto.setPersonalNotes(userMovie.getNotes());
        return movieDto;
    }
}
