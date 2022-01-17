package com.example.controller;

import com.example.service.WebSearchService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class WebSearchController {

    private final WebSearchService webSearchService;

    @GetMapping("/movie")
    public String movie(Model model) {
        model.addAttribute("movies", webSearchService.searchMoviesPopular());
        return "movie";
    }
}
