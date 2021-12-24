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

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<MovieDto>> findAllByUserId(@PathVariable("userId") @Min(1) Long userId) {
        List<MovieDto> movieDtoList = userMovieService.fetchAllByUserId(userId);
        return ResponseEntity.ok().body(movieDtoList);
    }

    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> countAllByUserId(@PathVariable("userId") @Min(1) Long userId) {
        return ResponseEntity.ok().body(userMovieService.countFavouriteByUserId(userId));
    }

    @GetMapping("/sort")
    public ResponseEntity<List<MovieDto>> sortByColumnNameAsc(@RequestParam(value = "sortColumn") String sortColumn,
                                                        @RequestParam(value = "page") int page,
                                                        @RequestParam(value = "size") int size) {
        List<MovieDto> movieDtoList = userMovieService.sortByColumnNameAsc(PageRequest.of(page, size, Sort.by(sortColumn).ascending()));
        return ResponseEntity.ok().body(movieDtoList);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MovieDto>> findAllFiltered(@RequestParam(value = "rating") Integer rating,
                                                          @RequestParam(value = "created") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate created,
                                                          @RequestParam(value = "page") int page,
                                                          @RequestParam(value = "size") int size) {

        List<MovieDto> movieDtoList = userMovieService.filterByRatingAfterAndCreatedAfter(rating, created, PageRequest.of(page, size));
        return ResponseEntity.ok().body(movieDtoList);
    }

    @GetMapping("/filterNew")
    public ResponseEntity<List<MovieDto>> findAllFilteredNew(@RequestParam(value = "search") String search,
                                                             @RequestParam(value = "page") int page,
                                                             @RequestParam(value = "size") int size) {

        List<MovieDto> movieDtoList = userMovieService.filterByNotesContainingAndViewedTrue(search, PageRequest.of(page, size));
        return ResponseEntity.ok().body(movieDtoList);
    }
}
