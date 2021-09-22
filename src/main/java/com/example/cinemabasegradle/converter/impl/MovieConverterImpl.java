package com.example.cinemabasegradle.converter.impl;

import com.example.cinemabasegradle.converter.MovieConverter;
import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.model.Genre;
import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.repository.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class MovieConverterImpl implements MovieConverter {

    private final GenreRepository genreRepository;

    @Override
    public Movie toModel(MovieDto movieDto) {
        if (movieDto == null) {
            return null;
        }
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setExternalId(movieDto.getExternalMovieId());
        movie.setPoster(movieDto.getPosterPath());
        movie.setPremierDate(movieDto.getReleaseDate());
        movie.setImdb(movieDto.getVoteAverage());
        movie.setDescription(movieDto.getOverview());
        movie.setAdult(movieDto.getAdult());


        List<Long> genreExternalIds = movieDto.getGenreIds();
        List<Genre> genres = genreRepository.findAllByExternalId(genreExternalIds);
        movie.setGenres(genres);
        return movie;
    }

    @Override
    public MovieDto toDto(Movie movie) {
        if (movie == null) {
            return null;
        }
        MovieDto movieDto = new MovieDto();
        movieDto.setExternalMovieId(movie.getExternalId());
        movieDto.setTitle(movie.getTitle());
        movieDto.setPosterPath(movie.getPoster());
        movieDto.setReleaseDate(movie.getPremierDate());
        movieDto.setVoteAverage(movie.getImdb());
        movieDto.setOverview(movie.getDescription());
        movieDto.setAdult(movie.getAdult());

        List<Genre> genres = movie.getGenres();
        List<Long> genresIds = genres.stream().map(Genre::getExternalId).collect(Collectors.toList());

        movieDto.setGenreIds(genresIds);
        return movieDto;
    }
}
