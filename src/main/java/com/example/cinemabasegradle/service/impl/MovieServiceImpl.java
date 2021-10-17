package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.converter.MovieConverter;
import com.example.cinemabasegradle.converter.UserMovieConverter;
import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.exception.ErrorMessages;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.MovieRepository;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final UserMovieRepository userMovieRepository;
    private final MovieConverter movieConverter;
    private final UserRepository userRepository;
    private final UserMovieConverter userMovieConverter;

    @Transactional
    @Override
    public Long addToFavouriteMovies(MovieDto movieDto) {
        Long userId = 3L;
        User user = userRepository
                .findByIdAndActiveTrue(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, userId)));

        Movie movie = movieRepository.findByExternalId(movieDto.getExternalMovieId())
                .orElse(movieRepository.save(movieConverter.toModel(movieDto)));

        UserMovie userMovie = userMovieConverter.createUserMovie(user, movie, movieDto);
        UserMovie saveUserMovie = userMovieRepository.save(userMovie);
        return saveUserMovie.getId();
    }

    @Override
    public MovieDto fetchFavouriteMovieById(Long id) {
        UserMovie userMovie = userMovieRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));

        return userMovieConverter.convertUserMovieToMovieDto(userMovie);
    }

    @Transactional
    @Override
    public void updateFavouriteMovie(MovieDto movieDto, Long userMovieId) {
        UserMovie userMovie = userMovieRepository
                .findById(userMovieId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, userMovieId)));
        userMovie.setRating(movieDto.getPersonalRating());
        userMovie.setNotes(movieDto.getPersonalNotes());
        userMovieRepository.save(userMovie);
    }

    @Transactional
    @Override
    public void deleteFavouriteMovie(Long id) {
        UserMovie userMovie = userMovieRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        userMovieRepository.delete(userMovie);
    }
}
