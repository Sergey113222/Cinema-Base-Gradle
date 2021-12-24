package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.mapper.UserMovieRowMapper;
import com.example.cinemabasegradle.model.UserMovie;
import com.example.cinemabasegradle.repository.UserMovieRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Profile({"jdbc", "testJdbc", "docker"})
@Repository
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public class UserMovieRepositoryJdbcImpl implements UserMovieRepository {

    private static final String ID = "id";
    private static final String RATING = "rating";
    private static final String NOTES = "notes";
    private static final String VIEWED = "viewed";
    private static final String USER_ID = "userId";
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";
    private static final String EXTERNAL_ID = "externalMovieId";

    @Value("${sql.user_movie.find_by_id}")
    private String findByIdQuery;
    @Value("${sql.user_movie.delete}")
    private String deleteQuery;
    @Value("${sql.user_movie.save}")
    private String saveUserMovieQuery;
    @Value("${sql.user_movie.update}")
    private String updateUserMovieQuery;
    @Value("${sql.user_movie.find_all_favourite_by_user_id}")
    private String findAllByUserIdQuery;
    @Value("${sql.user_movie.count_all_favourite_by_user_id}")
    private String countAllByUserIdQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UserMovieRowMapper userMovieRowMapper;

    @Override
    public UserMovie save(UserMovie userMovie) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, userMovie.getId())
                .addValue(EXTERNAL_ID, userMovie.getExternalMovieId())
                .addValue(RATING, userMovie.getRating())
                .addValue(NOTES, userMovie.getNotes())
                .addValue(VIEWED, userMovie.isViewed())
                .addValue(USER_ID, userMovie.getUser().getId())
                .addValue(CREATED, LocalDate.now())
                .addValue(UPDATED, LocalDate.now());
        if (userMovie.getId() == null) {
            namedParameterJdbcTemplate.update(saveUserMovieQuery, paramSource, holder);
            userMovie.setId((Objects.requireNonNull(holder.getKey())).longValue());

        } else {
            namedParameterJdbcTemplate.update(updateUserMovieQuery, paramSource);
        }
        return userMovie;
    }

    @Override
    public Optional<UserMovie> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        return namedParameterJdbcTemplate.query(findByIdQuery, params, userMovieRowMapper).stream().findAny();
    }

    @Override
    public void delete(UserMovie userMovie) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, userMovie.getId());
        namedParameterJdbcTemplate.update(deleteQuery, paramSource);
    }

    @Override
    public Optional<List<UserMovie>> findAllByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);
        List<UserMovie> userMovies = namedParameterJdbcTemplate.query(findAllByUserIdQuery, params, userMovieRowMapper);
        return Optional.ofNullable(userMovies);
    }

    @Override
    public Long countUserMovieByUserId(Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);
        return namedParameterJdbcTemplate.queryForObject(countAllByUserIdQuery, params, Long.class);
    }

    @Override
    public Page<UserMovie> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserMovie> findByRatingAfterAndCreatedAfter(Integer rating, LocalDate created, Pageable pageable) {
        return null;
    }

    @Override
    public Page<UserMovie> findByNotesContainingAndViewedTrue(String search, Pageable pageable) {
        return null;
    }
}
