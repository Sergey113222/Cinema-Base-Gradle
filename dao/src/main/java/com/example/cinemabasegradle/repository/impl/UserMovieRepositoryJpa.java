package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.UserMovie;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cinemabasegradle.repository.UserMovieRepository;

@Profile({"jpa", "testJpa"})
public interface UserMovieRepositoryJpa extends UserMovieRepository, JpaRepository<UserMovie, Long> {
}
