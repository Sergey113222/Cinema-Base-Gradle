package com.example.cinemabasegradle;

import com.example.cinemabasegradle.service.impl.GenreServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@RequiredArgsConstructor
public class CinemaBaseGradleApplication {
    private final GenreServiceImpl genreService;

    public static void main(String[] args) {
        SpringApplication.run(CinemaBaseGradleApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initGenre() throws JsonProcessingException {
        genreService.getGenresExternal();
    }
}
