package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("id"));
        genre.setName(resultSet.getString("name"));
        genre.setCreated(resultSet.getDate("created"));
        genre.setExternalId(resultSet.getLong("external_id"));
        return genre;
    }
}
