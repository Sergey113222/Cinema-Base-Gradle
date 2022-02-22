package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersRowMapper implements RowMapper<User> {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ACTIVE = "active";
    private static final String CREATED = "created";

    private static final String PROFILE_ID = "profile.id";
    private static final String AVATAR = "avatar";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String AGE = "age";
    private static final String LANGUAGE = "language";

    private static final String NAME = "name";

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        Profile profile = new Profile();
        Role role = new Role();

        user.setId(resultSet.getLong(ID));
        user.setUsername(resultSet.getString(USERNAME));
        user.setEmail(resultSet.getString(EMAIL));
        user.setPassword(resultSet.getString(PASSWORD));

        role.setName(resultSet.getString(NAME));

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        user.setRoles(roleList);
        user.setActive(resultSet.getBoolean(ACTIVE));
        if (resultSet.getDate(CREATED) != null) {
            user.setCreated(resultSet.getDate(CREATED).toLocalDate());
        }

        profile.setId(resultSet.getLong(PROFILE_ID));
        profile.setAvatar(resultSet.getString(AVATAR));
        profile.setFirstName(resultSet.getString(FIRST_NAME));
        profile.setLastName(resultSet.getString(LAST_NAME));
        profile.setAge(resultSet.getInt(AGE));
        profile.setLanguage(resultSet.getString(LANGUAGE));

        profile.setCreated(resultSet.getDate(CREATED) == null ? null : resultSet.getDate(CREATED).toLocalDate());

        user.setProfile(profile);

        return user;
    }
}
