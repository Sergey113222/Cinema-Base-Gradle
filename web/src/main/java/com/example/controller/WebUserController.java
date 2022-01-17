package com.example.controller;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.service.WebUserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class WebUserController {

    private final WebUserService webUserService;

    @GetMapping("/user")
    public String movie(Model model) {
        model.addAttribute("users", webUserService.findAllUsers());
        for (UserDto userDto : webUserService.findAllUsers()) {
            model.addAttribute("count", webUserService.countFavouriteByUserId(userDto.getId()));
        }
        return "user";
    }

    @GetMapping("/main")
    public String product(Model model) {
        model.addAttribute("main", null);
        return "main";
    }
}
