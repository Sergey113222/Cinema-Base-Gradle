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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Profile("jdbc")
@Repository
@RequiredArgsConstructor
public class UserRepositoryJdbcImpl implements UserRepository {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String USERNAME = "username";

    @Value("${sql.user.find_by_id}")
    private String findByIdQuery;
    @Value("${sql.user.find_by_email}")
    private String findByEmailQuery;
    @Value("${sql.user.find_by_username}")
    private String findByUsernameQuery;
    @Value("${sql.user.find_all}")
    private String findAllQuery;
    @Value("${sql.user.save}")
    private String saveQuery;
    @Value("${sql.user.update}")
    private String updateQuery;
    @Value("${sql.user.delete}")
    private String deleteQuery;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Optional<User> findById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, id);
        User user = namedParameterJdbcTemplate.queryForObject(findByIdQuery, params, new UsersRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public User findByUsername(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERNAME, username);
        return namedParameterJdbcTemplate.queryForObject(findByUsernameQuery, params, new UsersRowMapper());
    }

    @Override
    public List<User> findAll() {
        List<User> result = namedParameterJdbcTemplate.query(findAllQuery, new UsersRowMapper());
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL, email);
        User user = namedParameterJdbcTemplate.queryForObject(findByEmailQuery, params, new UsersRowMapper());
        return Optional.ofNullable(user);
    }

    @Override
    public User save(User user) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue(ID, user.getId());


        if (user.getId() == null) {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(saveQuery, paramSource, holder);
            user.setId(holder.getKey().longValue());
            return user;
        } else {
            namedParameterJdbcTemplate.update(updateQuery, paramSource);
        }
        return user;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
