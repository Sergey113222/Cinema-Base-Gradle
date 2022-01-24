package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.repository.impl.UserRepositoryJdbcImpl;
import com.example.cinemabasegradle.service.XmlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class XmlControllerTest {

    private MockMvc mockMvc;

    @Mock
    private XmlService xmlService;
    @Mock
    private UserRepositoryJdbcImpl userRepositoryJdbc;
    @InjectMocks
    private XmlController xmlController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(xmlController)
                .alwaysDo(MockMvcResultHandlers.print()).build();
    }

    @Test
    void marshalXml() throws Exception {
        mockMvc.perform(post("/xml/marshalXml"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void unmarshalXml() throws Exception {
        mockMvc.perform(post("/xml/unmarshalXml"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}