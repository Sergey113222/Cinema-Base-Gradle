package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.dto.AuthenticationRequestDto;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.service.JwtTokenProvider;
import com.example.cinemabasegradle.service.JwtUserDetailsService;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class AuthenticationRestControllerTest {

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMCIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaW";
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private JwtUserDetailsService jwtUserDetailsService;
    @Mock
    private User user;
    @InjectMocks
    private AuthenticationRestController authenticationRestController;

    private AuthenticationRequestDto requestDto;

    @BeforeEach
    void setUp() {
        requestDto = new AuthenticationRequestDto();
        requestDto.setEmail("test@mail.ru");
        requestDto.setPassword("test_pass");
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationRestController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print()).build();
        objectMapper = new ObjectMapper();
        List<Role> roleList = new ArrayList<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roleList.add(role);

        user = User.builder()
                .id(1L)
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .roles(roleList)
                .build();
    }

    @Test
    void auth() throws Exception {
        when(jwtUserDetailsService.loadUserByEmail(requestDto.getEmail(), requestDto.getPassword())).thenReturn(user);
        when(jwtTokenProvider.createToken(user.getId(), user.getRoles())).thenReturn(TOKEN);
        mockMvc.perform(post("/api/v1/auth")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        verify(jwtUserDetailsService).loadUserByEmail(requestDto.getEmail(), requestDto.getPassword());
        verify(jwtTokenProvider).createToken(user.getId(), user.getRoles());
    }
}