package com.example.cinemabasegradle.mapper;

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
    private static final String EXTERNAL_ID = "external_movie_id";


    @Override
    public UserMovie mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserMovie userMovie = new UserMovie();
        userMovie.setId(rs.getLong(ID));
        userMovie.setExternalMovieId(rs.getLong(EXTERNAL_ID));
        userMovie.setRating(rs.getInt(RATING));
        userMovie.setNotes(rs.getString(NOTES));
        userMovie.setViewed(rs.getBoolean(VIEWED));
       // userMovie.setCreated(rs.getDate(CREATED));



        User user = new User();
        user.setId(rs.getLong(ID));
        userMovie.setUser(user);
        return userMovie;
    }
}
