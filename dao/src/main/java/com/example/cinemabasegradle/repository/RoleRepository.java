package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.Role;

public interface RoleRepository {
    Role findByName(String name);
}
