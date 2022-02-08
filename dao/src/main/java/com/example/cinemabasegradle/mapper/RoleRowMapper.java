package com.example.cinemabasegradle.mapper;

import com.example.cinemabasegradle.model.Role;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper implements RowMapper<Role> {

    private static final String ROLE_ID = "id";
    private static final String NAME = "name";

    @Override
    public Role mapRow(ResultSet resultSet, int i) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getLong(ROLE_ID));
        role.setName(resultSet.getString(NAME));

        return role;
    }
}
