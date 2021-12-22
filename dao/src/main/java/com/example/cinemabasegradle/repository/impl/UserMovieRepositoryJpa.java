package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Profile({"jpa", "testJpa"})
public interface UserMovieRepositoryJpa extends UserMovieRepository, JpaRepository<UserMovie, Long> {

    Optional<List<UserMovie>> findAllByUserId(Long userId);

    Long countUserMovieByUserId(Long userId);
}
