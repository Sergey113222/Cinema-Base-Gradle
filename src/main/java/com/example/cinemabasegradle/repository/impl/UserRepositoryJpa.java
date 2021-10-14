package com.example.cinemabasegradle.repository.impl;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile("jpa")
@Repository
public interface UserRepositoryJpa extends UserRepository, JpaRepository<User, Long> {

    @Query("select u from User u where u.active = true and u.id = :id")
    Optional<User> findById(Long id);

    User findByUsername(String username);

    @Modifying
    @Query("update User u set u.active = false where u.id = :id")
    void deleteUser(@Param("id") Long id);

    List<User> findAll();

    @Query("select u from User u  where u.active = true and u.email = :email")
    Optional<User> findByEmail(String email);
}