package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemabasegradle.service.UserMovieService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/favourite")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class UserMovieController {

    private final UserMovieService userMovieService;

    @PostMapping
    public ResponseEntity<Long> addToFavouriteMovie(@RequestBody @Valid MovieDto movieDto) {
        Long createdMovieDtoId = userMovieService.addToFavouriteMovies(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovieDtoId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> findFavouriteMovieById(@PathVariable("id") @Min(1) Long id) {
        MovieDto movieDto = userMovieService.fetchFavouriteMovieById(id);
        return ResponseEntity.ok().body(movieDto);
    }

    @PutMapping
    public ResponseEntity<Void> updateFavouriteMovie(@RequestBody @Valid MovieDto movieDto,
                                                     @RequestParam @Min(1) Long userMovieId) {
        userMovieService.updateFavouriteMovie(movieDto, userMovieId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavouriteMovie(@PathVariable("id") @Min(1) Long id) {
        userMovieService.deleteFavouriteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
