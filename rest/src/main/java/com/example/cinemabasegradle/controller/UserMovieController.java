package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.cinemabasegradle.service.UserMovieService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

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
    public MovieDto findFavouriteMovieById(@PathVariable("id") @Min(1) Long id) {
        return userMovieService.fetchFavouriteMovieById(id);
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

    @GetMapping("/all/{userId}")
    public List<MovieDto> findAllByUserId(@PathVariable("userId") @Min(1) Long userId) {
        return userMovieService.fetchAllByUserId(userId);
    }

    @GetMapping("/count/{userId}")
    public Long countAllByUserId(@PathVariable("userId") @Min(1) Long userId) {
        return userMovieService.countFavouriteByUserId(userId);
    }

    @GetMapping("/sort")
    public List<MovieDto> sortByColumnNameAsc(
            @RequestParam(value = "sortColumn", defaultValue = "created") String sortColumn,
            @RequestParam(value = "page", defaultValue = "20") Integer page,
            @RequestParam(value = "size", defaultValue = "0") Integer size) {
        return userMovieService.sortByColumnNameAsc(PageRequest.of(page, size,
                Sort.by(sortColumn).ascending()));
    }

    @GetMapping("/filter")
    public List<MovieDto> findAllFiltered(
            @RequestParam(value = "rating", defaultValue = "0") Integer rating,
            @RequestParam(value = "created", defaultValue = "20-01-01")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate created,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {

        return userMovieService.filterByRatingAfterAndCreatedAfter(rating, created,
                PageRequest.of(page, size));
    }

    @GetMapping("/filterNew")
    public List<MovieDto> findAllFilteredNew(
            @RequestParam(value = "search", defaultValue = " ") String search,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {

        return userMovieService.filterByNotesContainingAndViewedTrue(search,
                PageRequest.of(page, size));
    }
}
