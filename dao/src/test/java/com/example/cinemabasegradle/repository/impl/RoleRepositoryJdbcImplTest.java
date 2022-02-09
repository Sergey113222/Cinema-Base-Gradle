package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.config.EmbeddedTestConfig;
import com.example.cinemabasegradle.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = EmbeddedTestConfig.class)
@ActiveProfiles("testJdbc")
@Transactional
class RoleRepositoryJdbcImplTest {
    @Autowired
    private RoleRepositoryJdbcImpl roleRepositoryJdbc;

    @Test
    void findByName() {
        Role role = roleRepositoryJdbc.findByName("ROLE_USER");
        assertEquals(1, role.getId());
        assertEquals("ROLE_USER", role.getName());
    }
}