package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.Genre;
import com.example.cinemabasegradle.repository.GenreRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Repository
public interface GenreRepositoryJpa extends GenreRepository, JpaRepository<Genre, Long> {
    Optional<Genre> findByExternalId(Long externalId);

    List<Genre> findAllByExternalIdIn(@Param("genreExternalIds") List<Long> genreExternalIds);

    void saveAll(List<Genre> externalGenreDtoList);
}
