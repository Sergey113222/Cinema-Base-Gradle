package com.example.cinemabasegradle.converter;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.model.Movie;

public interface MovieConverter {
    Movie toModel(MovieDto movieDto);

    MovieDto toDto(Movie movie);
}
