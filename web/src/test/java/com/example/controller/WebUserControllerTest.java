package com.example.controller;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.Role;
import com.example.service.WebUserService;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class WebUserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WebUserService webUserService;
    @InjectMocks
    private WebUserController webUserController;

    private UserDto userDto;
    private List<UserDto> userDtoList;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(webUserController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();

        userDto = UserDto.builder()
                .id(1L)
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .role(Role.ROLE_USER)
                .profileDto(ProfileDto.builder()
                        .id(1L)
                        .avatar("xxx")
                        .firstName("Serega")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build()).build();

        userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

    }

    @Test
    void movie() throws Exception {
        when(webUserService.findAllUsers()).thenReturn(userDtoList);

        mockMvc.perform(get("/user/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(webUserService, times(2)).findAllUsers();
        verify(webUserService, times(1)).countFavouriteByUserId(userDto.getId());
    }

    @Test
    void product() throws Exception {
        mockMvc.perform(get("/main/"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}