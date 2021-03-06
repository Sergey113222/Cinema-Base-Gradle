package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.dto.RabbitRequestDto;
import com.example.cinemabasegradle.exception.ErrorMessages;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.service.SearchService;
import com.example.cinemabasegradle.service.UserMovieService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class UserMovieServiceImpl implements UserMovieService {

    private final UserMovieRepository userMovieRepository;
    private final UserRepository userRepository;
    private final SearchService searchService;
    private final ProducerRabbitService producerRabbitService;

    @Override
    public Long addToFavouriteMovies(MovieDto movieDto) {
        Long userId = 1L;
        User user = userRepository
                .findByIdAndActiveTrue(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, userId)));

        UserMovie userMovie = new UserMovie();
        userMovie.setUser(user);
        userMovie.setRating(movieDto.getPersonalRating());
        userMovie.setNotes(movieDto.getPersonalNotes());
        userMovie.setExternalMovieId(movieDto.getExternalMovieId());

        UserMovie saveUserMovie = userMovieRepository.save(userMovie);

        RabbitRequestDto requestDto = new RabbitRequestDto();
        requestDto.setEmail(user.getEmail());
        requestDto.setTitle(movieDto.getTitle());

        producerRabbitService.produce(requestDto);
        return saveUserMovie.getId();
    }

    @Override
    public MovieDto fetchFavouriteMovieById(Long id) {
        UserMovie userMovie = userMovieRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        MovieDto movieDto = searchService.searchMoviesById(userMovie.getExternalMovieId());
        movieDto.setPersonalRating(userMovie.getRating());
        movieDto.setPersonalNotes(userMovie.getNotes());
        return movieDto;
    }

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

    @Override
    public void deleteFavouriteMovie(Long id) {
        UserMovie userMovie = userMovieRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        userMovieRepository.delete(userMovie);
    }
}
