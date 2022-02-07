package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.Role;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Profile({"jpa", "testJpa"})
@Repository
public interface RoleRepositoryJpa extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
