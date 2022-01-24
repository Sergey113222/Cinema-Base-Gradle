package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.UserMovie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserMovieRepository {

    UserMovie save(UserMovie userMovie);

    Optional<UserMovie> findById(Long id);

    void delete(UserMovie userMovie);

    List<UserMovie> findAllByUserId(Long userId);

    Long countUserMovieByUserId(Long userId);

    Page<UserMovie> findAll(Pageable pageable);

    Page<UserMovie> findByRatingAfterAndCreatedAfter(Integer rating, LocalDate created, Pageable pageable);

    Page<UserMovie> findByNotesContainingAndViewedTrue(String search, Pageable pageable);

}
