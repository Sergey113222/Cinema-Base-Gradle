package com.example.controller;

import com.example.service.WebSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class WebSearchController {

    private final WebSearchService webSearchService;

    @GetMapping("/movie")
    public String movie(Model model) {
        model.addAttribute("movies", webSearchService.searchMoviesPopular());
        return "movie";
    }
}
