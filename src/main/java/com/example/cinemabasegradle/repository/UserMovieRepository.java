package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.UserMovie;

import java.util.Optional;

public interface UserMovieRepository {

    UserMovie save(UserMovie userMovie);

   Optional<UserMovie> findById(Long id);

   void delete(UserMovie userMovie);
}
