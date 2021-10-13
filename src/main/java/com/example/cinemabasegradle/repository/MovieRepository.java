package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.Movie;

import java.util.Optional;

public interface MovieRepository {

    Optional<Movie> findByExternalId(Long externalId);

    Movie save(Movie movie);
}
