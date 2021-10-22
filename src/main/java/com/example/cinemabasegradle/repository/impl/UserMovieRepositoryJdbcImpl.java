package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.mapper.UserMovieRowMapper;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
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
public class UserMovieRepositoryJdbcImpl implements UserMovieRepository {

    private static final String ID = "id";
    private static final String RATING = "rating";
    private static final String NOTES = "notes";
    private static final String VIEWED = "viewed";
    private static final String USER_ID = "userId";
    private static final String MOVIE_ID = "movieId";
    private static final String CREATED = "created";

    @Value("${sql.user_movie.find_by_id}")
    private String findByIdQuery;
    @Value("${sql.user_movie.delete}")
    private String deleteQuery;
    @Value("${sql.user_movie.save}")
    private String saveUserMovieQuery;
    @Value("${sql.user_movie.update}")
    private String updateUserMovieQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserMovieRowMapper userMovieRowMapper;

    @Override
    public UserMovie save(UserMovie userMovie) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, userMovie.getId())
                .addValue(RATING, userMovie.getRating())
                .addValue(NOTES, userMovie.getNotes())
                .addValue(VIEWED, userMovie.isViewed())
                .addValue(USER_ID, userMovie.getUser().getId())
                .addValue(CREATED, new Date());
        if (userMovie.getId() == null) {
            namedParameterJdbcTemplate.update(saveUserMovieQuery, paramSource, holder);
            userMovie.setId(holder.getKey().longValue());
            return userMovie;
        } else {
            namedParameterJdbcTemplate.update(updateUserMovieQuery, paramSource);
        }
        return userMovie;
    }

    @Override
    public Optional<UserMovie> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        UserMovie userMovie = namedParameterJdbcTemplate.queryForObject(findByIdQuery, params, userMovieRowMapper);
        return Optional.ofNullable(userMovie);
    }

    @Override
    public void delete(UserMovie userMovie) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, userMovie.getId());
        namedParameterJdbcTemplate.update(deleteQuery, paramSource);
    }
}