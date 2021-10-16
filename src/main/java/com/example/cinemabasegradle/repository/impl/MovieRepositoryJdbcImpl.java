package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.exception.DaoException;
import com.example.cinemabasegradle.mapper.MovieRowMapper;
import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class MovieRepositoryJdbcImpl implements MovieRepository {

    private static final String EXTERNAL_ID = "externalId";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String POSTER = "poster";
    private static final String PREMIER_DATE = "premierDate";
    private static final String IMDB = "imdb";
    private static final String DESCRIPTION = "description";
    private static final String IS_ADULT = "isAdult";
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";


    @Value("${sql.movie.find_by_external_id}")
    private String findByExternalIdQuery;
    @Value("${sql.movie.save}")
    private String saveQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final MovieRowMapper movieRowMapper;

    @Override
    public Optional<Movie> findByExternalId(Long externalId) {
        try{
            Map<String, Object> params = new HashMap<>();
            params.put(EXTERNAL_ID, externalId);
            Movie movie = namedParameterJdbcTemplate.queryForObject(findByExternalIdQuery, params, movieRowMapper);
            return Optional.ofNullable(movie);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Movie save(Movie movie) {
        try {
            SqlParameterSource paramSource = new MapSqlParameterSource()
                    .addValue(ID, movie.getId())
                    .addValue(TITLE, movie.getTitle())
                    .addValue(POSTER, movie.getPoster())
                    .addValue(PREMIER_DATE, movie.getPremierDate())
                    .addValue(IMDB, movie.getImdb())
                    .addValue(DESCRIPTION, movie.getDescription())
                    .addValue(IS_ADULT, movie.getAdult())
                    .addValue(CREATED, new Date())
                    .addValue(UPDATED, movie.getUpdated())
                    .addValue(EXTERNAL_ID, movie.getExternalId());

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(saveQuery, paramSource, holder);
            movie.setId(holder.getKey().longValue());
            return movie;
        } catch (DataAccessException e) {
            throw new DaoException("Problems with saving the movie");
        }
    }
}
