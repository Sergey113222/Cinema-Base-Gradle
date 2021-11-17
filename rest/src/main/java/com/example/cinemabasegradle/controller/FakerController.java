package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.impl.UserRepositoryJdbcImpl;
import com.example.cinemabasegradle.service.FakerService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/faker")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class FakerController {
    private final FakerService fakerService;
    private final UserRepositoryJdbcImpl userRepositoryJdbc;
    private final StopWatch timer = new StopWatch();

    @PostMapping(value = "/generatorSlow")
    public void generatorSlow(@RequestParam Integer quantity) {
        timer.start();
        for (int i = 0; i < quantity; i++) {
            userRepositoryJdbc.save(fakerService.createFakeUser());
        }
        timer.stop();
    }

    @PostMapping(value = "/generatorFast")
    public void generatorFast(@RequestParam Integer quantity) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            userList.add(fakerService.createFakeUser());
        }
        timer.start();
        userRepositoryJdbc.saveAllUser(userList);
        timer.stop();
    }

    @PostMapping(value = "/deleter")
    public void deleter() {
        userRepositoryJdbc.deleteAllUser();
    }
}
