package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.dto.RoleDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class RegistrationControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private RegistrationController registrationController;

    private UserDto userDto;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(registrationController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();
        objectMapper = new ObjectMapper();
        List<RoleDto> roleDtoList = new ArrayList<>();
        RoleDto roleDto = new RoleDto();
        roleDto.setName("ROLE_USER");
        roleDtoList.add(roleDto);

        userDto = UserDto.builder()
                .id(1L)
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .roles(roleDtoList)
                .profileDto(ProfileDto.builder()
                        .id(1L)
                        .avatar("xxx")
                        .firstName("Serega")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build()).build();
    }

    @Test
    void register() throws Exception {

        mockMvc.perform(post("/registration/new")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userService).createUser(any());
    }

    @Test
    void updateUser() throws Exception {
        userDto.setEmail("update@mail.ru");
        userDto.setId(userDto.getId());
        mockMvc.perform(put("/registration/update")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(userService).updateUser(any());

    }
}