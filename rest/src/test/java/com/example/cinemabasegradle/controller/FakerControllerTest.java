package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.repository.impl.UserRepositoryJdbcImpl;
import com.example.cinemabasegradle.service.impl.FakerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class FakerControllerTest {

    private MockMvc mockMvc;
    private FakerServiceImpl fakerService;
    private UserRepositoryJdbcImpl userRepositoryJdbc;

    @BeforeEach
    void setUp() {

        fakerService = mock(FakerServiceImpl.class);
        userRepositoryJdbc = mock(UserRepositoryJdbcImpl.class);
        FakerController fakerController = new FakerController(fakerService, userRepositoryJdbc);

        mockMvc = MockMvcBuilders.standaloneSetup(fakerController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();


    }

    @Test
    void generatorSlow() throws Exception {
        mockMvc.perform(post("/faker/generatorSlow")
                        .param("quantity", String.valueOf(5))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(fakerService, times(5)).createFakeUser();
        verify(userRepositoryJdbc, times(5)).save(any());
    }

    @Test
    void generatorFast() throws Exception {
        mockMvc.perform(post("/faker/generatorFast")
                        .param("quantity", String.valueOf(5))
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(fakerService, times(5)).createFakeUser();
        verify(userRepositoryJdbc).saveAllUser(any());
    }

    @Test
    void deleter() throws Exception {
        mockMvc.perform(post("/faker/deleter")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userRepositoryJdbc, times(1)).deleteAllUser();
    }
}