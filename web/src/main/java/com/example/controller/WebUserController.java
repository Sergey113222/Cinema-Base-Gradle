package com.example.controller;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.service.WebUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
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
