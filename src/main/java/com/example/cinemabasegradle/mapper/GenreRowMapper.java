package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GenreRowMapper implements RowMapper<Genre> {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CREATED = "created";
    private static final String EXTERNAL_ID = "external_id";

    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong(ID));
        genre.setName(resultSet.getString(NAME));
        genre.setCreated(resultSet.getDate(CREATED));
        genre.setExternalId(resultSet.getLong(EXTERNAL_ID));
        return genre;
    }
}
