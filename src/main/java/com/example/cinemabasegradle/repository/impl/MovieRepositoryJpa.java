package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.repository.MovieRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Profile("jpa")
@Repository
public interface MovieRepositoryJpa extends MovieRepository,JpaRepository<Movie, Long> {
    @Query("select m from Movie m where m.externalId = :externalId")
    Optional<Movie> findByExternalId(Long externalId);
}
