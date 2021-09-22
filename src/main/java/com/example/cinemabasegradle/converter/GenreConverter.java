package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.ExternalGenreDto;
import com.example.cinemabasegradle.model.Genre;

import java.util.List;

public interface GenreConverter {
    List<Genre> toModel(List<ExternalGenreDto> externalGenreDtoList);

    List<ExternalGenreDto> toDto(List<Genre> genreList);
}
