package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.UserDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemabasegradle.service.UserService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/registration")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class RegistrationController {

    private final UserService userService;

    @PostMapping(value = "/new")
    public void register(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
    }

    @PutMapping(value = "/update")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public void updateUser(@RequestBody @Valid UserDto userDto) {
        userService.updateUser(userDto);
    }
}
