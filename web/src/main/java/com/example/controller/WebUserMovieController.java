package com.example.controller;

import com.example.cinemabasegradle.dto.MovieDto;
import com.example.service.WebUserMovieService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class WebUserMovieController {

    private final WebUserMovieService webUserMovieService;

    @GetMapping("/favourite/{id}")
    public String movie(Model model, @PathVariable("id") Long id) {
        model.addAttribute("id", id);
        model.addAttribute("movies", webUserMovieService.fetchAllByUserId(id));
        return "favourite";
    }

    @GetMapping("/movie/add/{id}")
    public String viewAddMoviePage(Model model, @PathVariable Long id) {
        MovieDto movie = new MovieDto();
        movie.setExternalMovieId(id);
        model.addAttribute("movie", movie);
        return "add";
    }

    @PostMapping("/favourite")
    public String addFavouriteMovie(MovieDto movie) {
        webUserMovieService.addToFavouriteMovies(movie);
        return "redirect:/movie";
    }

    @GetMapping("/favourite/edit/{id}")
    public String getFavouriteMovie(Model model, @PathVariable Long id) {
        webUserMovieService.fetchFavouriteMovieByUserId(id);
        model.addAttribute("movie", webUserMovieService.fetchFavouriteMovieByUserId(id));
        return "edit";
    }

    @PostMapping("/favourite/delete/{id}")
    public String deleteFavouriteMovie(@PathVariable Long id) {
        webUserMovieService.deleteFavouriteMovie(id);
        return "redirect:/user";
    }


    @PostMapping("/favourite/edit/{id}")
    public String editFavouriteMovie(MovieDto movie, @PathVariable Long id) {
        webUserMovieService.updateFavouriteMovie(movie, id);
        return "redirect:/user";
    }

}
