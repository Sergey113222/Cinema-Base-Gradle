package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Repository
public interface UserRepositoryJpa extends UserRepository, JpaRepository<User, Long> {

    Optional<User> findByIdAndActiveTrue(Long id);

    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.active = false where u.id = :id")
    void deleteUser(@Param("id") Long id);

    List<User> findAll();

    Optional<User> findByEmailAndActiveTrue(String email);
}
