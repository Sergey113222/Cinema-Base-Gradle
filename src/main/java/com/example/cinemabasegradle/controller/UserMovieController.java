package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.service.UserMovieService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/favourite")
public class UserMovieController {

    private final UserMovieService userMovieService;

    @PostMapping
    @ApiOperation(value = "adds a movie to an authenticated user (you can leave your rating and feedback)")
    public ResponseEntity<Long> addToFavouriteMovie(@RequestBody @Valid MovieDto movieDto) {
        Long createdMovieDtoId = userMovieService.addToFavouriteMovies(movieDto);
        return ResponseEntity.status(CREATED).body(createdMovieDtoId);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "finds the user's favorite movie")
    public ResponseEntity<MovieDto> findFavouriteMovieById(@PathVariable("id") @Min(1) Long id) {
        MovieDto movieDto = userMovieService.fetchFavouriteMovieById(id);
        return ResponseEntity.ok().body(movieDto);
    }

    @PutMapping
    @ApiOperation(value = "update the user's favorite movie (rating and feedback)")
    public ResponseEntity<MovieDto> updateFavouriteMovie(@RequestBody @Valid MovieDto movieDto, @RequestParam @Min(1) Long userMovieId) {
        userMovieService.updateFavouriteMovie(movieDto, userMovieId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete the user's favorite movie")
    public ResponseEntity<MovieDto> deleteFavouriteMovie(@PathVariable("id") @Min(1) Long id) {
        userMovieService.deleteFavouriteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
