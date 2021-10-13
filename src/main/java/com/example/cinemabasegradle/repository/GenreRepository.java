package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.Genre;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findByExternalId(Long externalId);

    List<Genre> findAllByExternalId(@Param("genreExternalIds") List<Long> genreExternalIds);

    void saveAll(List<Genre> externalGenreDtoList);

    long count();
}
