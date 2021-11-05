package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile("jpa")
public interface UserMovieRepositoryJpa extends UserMovieRepository, JpaRepository<UserMovie, Long> {
}
