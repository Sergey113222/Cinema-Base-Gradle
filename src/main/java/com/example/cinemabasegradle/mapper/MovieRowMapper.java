package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Movie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getLong("id"));
        movie.setTitle(resultSet.getString("title"));
        movie.setPoster(resultSet.getString("poster"));
        movie.setPremierDate(resultSet.getDate("premier_date"));
        movie.setImdb(resultSet.getDouble("imdb"));
        movie.setDescription(resultSet.getString("description"));
        movie.setAdult(resultSet.getBoolean("is_adult"));
        movie.setCreated(resultSet.getDate("created"));
        movie.setExternalId(resultSet.getLong("external_id"));
        return movie;
    }
}
