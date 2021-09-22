package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.GenreConverter;
import com.example.cinemabasegradle.dto.ExternalGenreDto;
import com.example.cinemabasegradle.model.Genre;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreConverterImpl implements GenreConverter {
    @Override
    public List<Genre> toModel(List<ExternalGenreDto> externalGenreDtoList) {

        return externalGenreDtoList.stream().map(externalGenreDto -> {
            Genre genre = new Genre();
            genre.setExternalId(externalGenreDto.getExternalId());
            genre.setName(externalGenreDto.getName());
            return genre;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ExternalGenreDto> toDto(List<Genre> genreList) {

        return genreList.stream().map(genre -> {
            ExternalGenreDto externalGenreDto = new ExternalGenreDto();
            externalGenreDto.setExternalId(genre.getExternalId());
            externalGenreDto.setName(genre.getName());
            return externalGenreDto;
        }).collect(Collectors.toList());
    }
}
