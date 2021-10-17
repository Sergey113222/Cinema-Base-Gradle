package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.User;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByIdAndActiveTrue(Long id);

    User findByUsername(String username);

    List<User> findAll();

    Optional<User> findByEmailAndActiveTrue(String email);

    User save(User user);

    void deleteUser(@Param("id") Long id);
}
