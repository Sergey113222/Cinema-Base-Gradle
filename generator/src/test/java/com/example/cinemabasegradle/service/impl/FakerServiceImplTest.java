package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FakerServiceImplTest {

    private FakerServiceImpl fakerService;

    @Test
    void createFakeUser() {
        fakerService = new FakerServiceImpl();
        User fakeUser = fakerService.createFakeUser();
        assertNotNull(fakeUser.getUsername());
        assertNotNull(fakeUser.getEmail());
    }
}