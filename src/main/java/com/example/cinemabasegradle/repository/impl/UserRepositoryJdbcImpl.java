package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.mapper.UsersRowMapper;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String PROFILE_ID = "profileId";
    private static final String ACTIVE = "active";
    private static final String CREATED = "created";

    private static final String AVATAR = "avatar";
    private static final String ABOUT = "about";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String AGE = "age";
    private static final String GENDER = "gender";
    private static final String REGION = "region";
    private static final String LANGUAGE = "language";

    @Value("${sql.user.find_by_id}")
    private String findByIdQuery;
    @Value("${sql.user.find_by_email}")
    private String findByEmailQuery;
    @Value("${sql.user.find_by_username}")
    private String findByUsernameQuery;
    @Value("${sql.user.find_all}")
    private String findAllQuery;
    @Value("${sql.user.save_user}")
    private String saveUserQuery;
    @Value("${sql.user.save_profile}")
    private String saveProfileQuery;
    @Value("${sql.user.delete}")
    private String deleteQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UsersRowMapper usersRowMapper;

    @Override
    public Optional<User> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        User user = namedParameterJdbcTemplate.queryForObject(findByIdQuery, params, usersRowMapper);
        return Optional.ofNullable(user);
    }

    @Override
    public User findByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERNAME, username);
        return namedParameterJdbcTemplate.queryForObject(findByUsernameQuery, params, usersRowMapper);
    }

    @Override
    public List<User> findAll() {
        List<User> result = namedParameterJdbcTemplate.query(findAllQuery, usersRowMapper);
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL, email);
        User user = namedParameterJdbcTemplate.queryForObject(findByEmailQuery, params, usersRowMapper);
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, user.getId())
                .addValue(USERNAME, user.getUsername())
                .addValue(PASSWORD, user.getPassword())
                .addValue(ROLE, user.getRole().toString())
                .addValue(ACTIVE, user.getActive())
                .addValue(CREATED, new Date())
                .addValue(EMAIL, user.getEmail())
                .addValue(AVATAR, user.getProfile().getAvatar())
                .addValue(ABOUT, user.getProfile().getAbout())
                .addValue(FIRST_NAME, user.getProfile().getFirstName())
                .addValue(LAST_NAME, user.getProfile().getLastName())
                .addValue(AGE, user.getProfile().getAge())
                .addValue(GENDER, user.getProfile().getGender())
                .addValue(REGION, user.getProfile().getRegion())
                .addValue(LANGUAGE, user.getProfile().getLanguage())
                .addValue(CREATED, new Date());

        namedParameterJdbcTemplate.update(saveProfileQuery, paramSource, holder);

        SqlParameterSource paramSource1 = new MapSqlParameterSource()
                .addValue(ID, user.getId())
                .addValue(USERNAME, user.getUsername())
                .addValue(PASSWORD, user.getPassword())
                .addValue(ROLE, user.getRole().toString())
                .addValue(ACTIVE, user.getActive())
                .addValue(PROFILE_ID, holder.getKey().longValue())
                .addValue(CREATED, new Date())
                .addValue(EMAIL, user.getEmail())
                .addValue(AVATAR, user.getProfile().getAvatar())
                .addValue(ABOUT, user.getProfile().getAbout())
                .addValue(FIRST_NAME, user.getProfile().getFirstName())
                .addValue(LAST_NAME, user.getProfile().getLastName())
                .addValue(AGE, user.getProfile().getAge())
                .addValue(GENDER, user.getProfile().getGender())
                .addValue(REGION, user.getProfile().getRegion())
                .addValue(LANGUAGE, user.getProfile().getLanguage())
                .addValue(CREATED, new Date());

        namedParameterJdbcTemplate.update(saveUserQuery, paramSource1, holder);
        user.setId(holder.getKey().longValue());
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, id);
        namedParameterJdbcTemplate.update(deleteQuery, paramSource);
    }
}
