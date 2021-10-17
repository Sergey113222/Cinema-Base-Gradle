package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.exception.ErrorMessages;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.mapper.GenreRowMapper;
import com.example.cinemabasegradle.model.Genre;
import com.example.cinemabasegradle.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class GenreRepositoryJdbcImpl implements GenreRepository {

    private static final String EXTERNAL_ID = "externalId";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";

    @Value("${sql.genre.find_by_external_id}")
    private String findByExternalIdQuery;
    @Value("${sql.genre.save}")
    private String saveQuery;
    @Value("${sql.genre.update}")
    private String updateQuery;
    @Value("${sql.genre.count}")
    private String countQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GenreRowMapper genreRowMapper;

    @Override
    public Optional<Genre> findByExternalId(Long externalId) {
        Map<String, Object> params = new HashMap<>();
        params.put(EXTERNAL_ID, externalId);
        Genre genre = namedParameterJdbcTemplate.queryForObject(findByExternalIdQuery, params, genreRowMapper);
        return Optional.ofNullable(genre);
    }

    @Override
    public List<Genre> findAllByExternalIdIn(List<Long> genreExternalIds) {
        List<Genre> result = new ArrayList<>();
        for (Long externalId : genreExternalIds) {
            result.add(this.findByExternalId(externalId).orElseThrow(() ->
                    new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, externalId))));
        }
        return result;
    }

    @Override
    public void saveAll(List<Genre> externalGenreDtoList) {
        for (Genre genre : externalGenreDtoList) {
            this.save(genre);
        }
    }

    @Override
    public long count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        namedParameterJdbcTemplate.query(countQuery, countCallback);
        return countCallback.getRowCount();
    }

    public Genre save(Genre genre) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, genre.getId())
                .addValue(NAME, genre.getName())
                .addValue(CREATED, genre.getCreated())
                .addValue(UPDATED, genre.getUpdated())
                .addValue(EXTERNAL_ID, genre.getExternalId());

        if (genre.getId() == null) {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(saveQuery, paramSource, holder);
            genre.setId(holder.getKey().longValue());
            return genre;
        } else {
            namedParameterJdbcTemplate.update(updateQuery, paramSource);
        }
        return genre;
    }
}
