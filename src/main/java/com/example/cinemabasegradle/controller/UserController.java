package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/users")
@Api(tags = "controller-only-for-ADMIN")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    @RolesAllowed("ADMIN")
    @ApiOperation(value = "Find User by id")
    public UserDto findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping(value = "/param")
    @RolesAllowed("ADMIN")
    @ApiOperation(value = "Find all Users")
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    @ApiOperation(value = "Delete User by id")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

}

