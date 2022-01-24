package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.impl.UserRepositoryJdbcImpl;
import com.example.cinemabasegradle.service.impl.FakerServiceImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Profile("jdbc")
@RequiredArgsConstructor
@RequestMapping(value = "/faker")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class FakerController {
    private final FakerServiceImpl fakerService;
    private final UserRepositoryJdbcImpl userRepositoryJdbc;

    @PostMapping(value = "/generatorSlow")
    public void generatorSlow(@RequestParam Integer quantity) {
        for (int i = 0; i < quantity; i++) {
            userRepositoryJdbc.save(fakerService.createFakeUser());
        }
    }

    @PostMapping(value = "/generatorFast")
    public void generatorFast(@RequestParam Integer quantity) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            userList.add(fakerService.createFakeUser());
        }
        userRepositoryJdbc.saveAllUser(userList);
    }

    @PostMapping(value = "/deleter")
    public void deleter() {
        userRepositoryJdbc.deleteAllUser();
    }
}
