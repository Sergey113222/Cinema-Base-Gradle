package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.mapper.UsersRowMapper;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
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

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class UserRepositoryJdbcImpl implements UserRepository {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String ACTIVE = "active";
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";

    private static final String AVATAR = "avatar";
    private static final String USER_ID = "userId";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String AGE = "age";
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
    @Value("${sql.user.update_user}")
    private String updateUserQuery;
    @Value("${sql.user.update_profile}")
    private String updateProfileQuery;
    @Value("${sql.user.delete}")
    private String deleteQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final UsersRowMapper usersRowMapper;

    @Override
    public Optional<User> findByIdAndActiveTrue(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        return namedParameterJdbcTemplate.query(findByIdQuery, params, usersRowMapper).stream().findAny();
    }

    @Override
    public User findByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERNAME, username);
        return namedParameterJdbcTemplate.queryForObject(findByUsernameQuery, params, usersRowMapper);
    }

    @Override
    public List<User> findAll() {
        return namedParameterJdbcTemplate.query(findAllQuery, usersRowMapper);
    }

    @Override
    public Optional<User> findByEmailAndActiveTrue(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL, email);
        User user = namedParameterJdbcTemplate.queryForObject(findByEmailQuery, params, usersRowMapper);
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource paramSourceUser = new MapSqlParameterSource()
                .addValue(ID, user.getId())
                .addValue(USERNAME, user.getUsername())
                .addValue(PASSWORD, user.getPassword())
                .addValue(ROLE, user.getRole().toString())
                .addValue(ACTIVE, user.getActive())
                .addValue(CREATED, LocalDate.now())
                .addValue(UPDATED, LocalDate.now())
                .addValue(EMAIL, user.getEmail());

        if (user.getId() == null) {
            namedParameterJdbcTemplate.update(saveUserQuery, paramSourceUser, holder);
                user.setId((Objects.requireNonNull(holder.getKey())).longValue());
        } else {
            namedParameterJdbcTemplate.update(updateUserQuery, paramSourceUser);
        }

        SqlParameterSource paramSourceProfile = new MapSqlParameterSource()
                .addValue(ID, user.getProfile().getId())
                .addValue(USER_ID, user.getId())
                .addValue(AVATAR, user.getProfile().getAvatar())
                .addValue(FIRST_NAME, user.getProfile().getFirstName())
                .addValue(LAST_NAME, user.getProfile().getLastName())
                .addValue(AGE, user.getProfile().getAge())
                .addValue(LANGUAGE, user.getProfile().getLanguage())
                .addValue(CREATED, LocalDate.now())
                .addValue(UPDATED, LocalDate.now());

        if (user.getProfile().getId() == null) {
            namedParameterJdbcTemplate.update(saveProfileQuery, paramSourceProfile, holder);
                user.getProfile().setId((Objects.requireNonNull(holder.getKey())).longValue());
        } else {
            namedParameterJdbcTemplate.update(updateProfileQuery, paramSourceProfile);
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, id);
        namedParameterJdbcTemplate.update(deleteQuery, paramSource);
    }
}
