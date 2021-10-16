package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Movie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MovieRowMapper implements RowMapper<Movie> {

    private static final String EXTERNAL_ID = "external_id";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String POSTER = "poster";
    private static final String PREMIER_DATE = "premier_date";
    private static final String IMDB = "imdb";
    private static final String DESCRIPTION = "description";
    private static final String IS_ADULT = "is_adult";
    private static final String CREATED = "created";

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getLong(ID));
        movie.setTitle(resultSet.getString(TITLE));
        movie.setPoster(resultSet.getString(POSTER));
        movie.setPremierDate(resultSet.getDate(PREMIER_DATE));
        movie.setImdb(resultSet.getDouble(IMDB));
        movie.setDescription(resultSet.getString(DESCRIPTION));
        movie.setAdult(resultSet.getBoolean(IS_ADULT));

        movie.setCreated(resultSet.getDate(CREATED));
        movie.setExternalId(resultSet.getLong(EXTERNAL_ID));
        return movie;
    }
}
