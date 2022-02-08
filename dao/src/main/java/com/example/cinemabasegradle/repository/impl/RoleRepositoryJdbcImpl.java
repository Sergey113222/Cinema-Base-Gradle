package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.mapper.RoleRowMapper;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.repository.RoleRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Profile({"jdbc", "testJdbc", "docker"})
@Repository
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public class RoleRepositoryJdbcImpl implements RoleRepository {

    @Value("${sql.role.find_by_name}")
    private String findByNameQuery;

    private static final String NAME = "name";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final RoleRowMapper roleRowMapper;

    @Override
    public Role findByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put(NAME, name);
        return namedParameterJdbcTemplate.queryForObject(findByNameQuery, params, roleRowMapper);
    }
}
