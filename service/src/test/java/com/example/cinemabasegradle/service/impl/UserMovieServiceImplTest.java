package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ActiveProfiles("jdbc")
class UserMovieServiceImplTest {

    private UserMovieRepository userMovieRepository;
    private UserRepository userRepository;
    private SearchService searchService;
    private UserMovieServiceImpl userMovieService;
    private ProducerRabbitService producerRabbitService;

    private final UserMovie userMovie;
    private final User user;
    private final MovieDto movieDto;
    private List<UserMovie> userMovieList;

    {
        user = new User();
        user.setId(2L);
        user.setUsername("Maxim");
        user.setPassword("G113222");
    }

    {
        userMovie = new UserMovie();
        userMovie.setId(1L);
        userMovie.setRating(9);
        userMovie.setNotes("it is my favourite movie!!!!");
        userMovie.setViewed(true);
        userMovie.setUser(new User());
    }

    {
        movieDto = new MovieDto();
        movieDto.setExternalMovieId(2569L);
        movieDto.setPersonalRating(9);
        movieDto.setPersonalNotes("it is my favourite movie!!!!");
    }

    {
        userMovieList = new ArrayList<>();
        userMovieList.add(userMovie);
    }


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMovieRepository = mock(UserMovieRepository.class);
        searchService = mock(SearchService.class);
        producerRabbitService = mock(ProducerRabbitService.class);
        userMovieService = new UserMovieServiceImpl(userMovieRepository, userRepository,
                searchService, producerRabbitService);
    }

    @Test
    void addToFavouriteMovies() {
        when(userRepository.findByIdAndActiveTrue(any())).thenReturn(Optional.ofNullable(user));
        when(userMovieRepository.save(any())).thenReturn(userMovie);
        userMovieService.addToFavouriteMovies(movieDto);
        verify(userMovieRepository).save(any());
    }

    @Test
    void fetchFavouriteMovieById() {
        when(userMovieRepository.findById(any())).thenReturn(Optional.of(userMovie));
        when(searchService.searchMoviesById(any())).thenReturn(movieDto);
        MovieDto movieDto = userMovieService.fetchFavouriteMovieById(userMovie.getId());
        assertNotNull(userMovie.getId());
        assertEquals(userMovie.getRating(), movieDto.getPersonalRating());
        assertEquals(userMovie.getNotes(), movieDto.getPersonalNotes());
        verify(userMovieRepository).findById(any());
    }

    @Test
    void updateFavouriteMovie() {
        when(userMovieRepository.findById(userMovie.getId())).thenReturn(Optional.of(userMovie));
        when(searchService.searchMoviesById(any())).thenReturn(movieDto);
        MovieDto movieDto = userMovieService.fetchFavouriteMovieById(userMovie.getId());
        userMovieService.updateFavouriteMovie(movieDto, userMovie.getId());
        verify(userMovieRepository).save(any());
    }

    @Test
    void deleteFavouriteMovie() {
        when(userMovieRepository.findById(userMovie.getId())).thenReturn(Optional.of(userMovie));
        userMovieService.deleteFavouriteMovie(1L);
        verify(userMovieRepository).delete(userMovie);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when userMovie doesn't exist.")
    void throwExceptionWhenFindById() {
        final long nonExistingId = 12902450235L;

        doReturn(Optional.empty()).when(userMovieRepository).findById(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userMovieService.fetchFavouriteMovieById(nonExistingId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when userMovie doesn't exist.")
    void throwExceptionWhenFetchFavouriteMovieById() {
        final long nonExistingId = 12902450235L;

        doReturn(Optional.empty()).when(userMovieRepository).findById(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userMovieService.fetchFavouriteMovieById(nonExistingId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when userMovie doesn't exist.")
    void throwExceptionWhenDeleteFavouriteMovie() {
        final long nonExistingId = 12902450235L;
        doReturn(Optional.empty()).when(userMovieRepository).findById(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userMovieService.deleteFavouriteMovie(nonExistingId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when userMovie doesn't exist.")
    void throwExceptionWhenUpdateFavouriteMovie() {
        final long nonExistingId = 12902450235L;
        doReturn(Optional.empty()).when(userMovieRepository).findById(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userMovieService.updateFavouriteMovie(movieDto, nonExistingId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when userId doesn't exist.")
    void throwExceptionWhenFindAllByUserId() {
        final long nonExistingId = 12902450235L;
        doReturn(Optional.empty()).when(userMovieRepository).findAllByUserId(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userMovieService.fetchAllByUserId(nonExistingId));
    }

    @Test
    void fetchAllByUserId() {
        when(userMovieRepository.findAllByUserId(any())).thenReturn(Optional.of(userMovieList));
        when(searchService.searchMoviesById(any())).thenReturn(movieDto);
        List<MovieDto> movieDtoList = userMovieService.fetchAllByUserId(userMovie.getId());
        assertTrue(movieDtoList.size() > 0);
    }

    @Test
    void countFavouriteByUserId() {
        when(userMovieRepository.countUserMovieByUserId(any())).thenReturn(1L);
        assertEquals(1, userMovieRepository.countUserMovieByUserId(10L));
    }
}