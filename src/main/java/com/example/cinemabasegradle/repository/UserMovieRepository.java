package com.example.cinemabasegradle.repository;

import com.example.cinemabasegradle.model.UserMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMovieRepository extends JpaRepository<UserMovie, Long> {
}
