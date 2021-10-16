package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Movie;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.model.UserMovie;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class UserMovieRowMapper implements RowMapper<UserMovie> {

    private static final String ID = "id";
    private static final String RATING = "rating";
    private static final String NOTES = "notes";
    private static final String VIEWED = "viewed";
    private static final String CREATED = "created";

    private static final String EXTERNAL_ID = "external_id";
    private static final String TITLE = "title";
    private static final String POSTER = "poster";
    private static final String PREMIER_DATE = "premier_date";
    private static final String IMDB = "imdb";
    private static final String DESCRIPTION = "description";
    private static final String IS_ADULT = "is_adult";

    @Override
    public UserMovie mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMovie userMovie = new UserMovie();
        userMovie.setId(rs.getLong(ID));
        userMovie.setRating(rs.getInt(RATING));
        userMovie.setNotes(rs.getString(NOTES));
        userMovie.setViewed(rs.getBoolean(VIEWED));
        userMovie.setCreated(rs.getDate(CREATED));

        Movie movie = new Movie();
        movie.setId(rs.getLong(ID));
        movie.setTitle(rs.getString(TITLE));
        movie.setPoster(rs.getString(POSTER));
        movie.setPremierDate(rs.getDate(PREMIER_DATE));
        movie.setImdb(rs.getDouble(IMDB));
        movie.setDescription(rs.getString(DESCRIPTION));
        movie.setAdult(rs.getBoolean(IS_ADULT));

        movie.setCreated(rs.getDate(CREATED));
        movie.setExternalId(rs.getLong(EXTERNAL_ID));
        userMovie.setMovie(movie);

        User user = new User();
        user.setId(rs.getLong(ID));
        userMovie.setUser(user);
        return userMovie;
    }
}
