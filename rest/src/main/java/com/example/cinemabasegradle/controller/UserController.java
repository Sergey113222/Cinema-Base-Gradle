package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.UserDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.cinemabasegradle.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public UserDto findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}

