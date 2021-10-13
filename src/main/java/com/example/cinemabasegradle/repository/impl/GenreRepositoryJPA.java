package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.Genre;
import com.example.cinemabasegradle.repository.GenreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
public interface GenreRepositoryJPA extends GenreRepository, JpaRepository<Genre, Long> {
    @Query("select g from Genre g where g.externalId = :externalId")
    Optional<Genre> findByExternalId(Long externalId);

    @Query("select g from Genre g where g.externalId IN :genreExternalIds")
    List<Genre> findAllByExternalId(@Param("genreExternalIds") List<Long> genreExternalIds);
}
