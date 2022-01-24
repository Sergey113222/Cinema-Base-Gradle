package com.example.service.impl;

import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WebUserServiceImplTest {

    private RestTemplate restTemplate;
    private WebUserServiceImpl webUserService;
    private List<UserDto> userDtos;
    private URI uri;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        webUserService = new WebUserServiceImpl(restTemplate);
        ReflectionTestUtils.setField(webUserService, "scheme", "http");
        ReflectionTestUtils.setField(webUserService, "host", "localhost");
        ReflectionTestUtils.setField(webUserService, "port", 8080);
        ReflectionTestUtils.setField(webUserService, "allUsers", "/users");
        ReflectionTestUtils.setField(webUserService, "favouriteCount", "/count/");


        UserDto userDto = UserDto.builder()
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

        userDtos = new ArrayList<>();
        userDtos.add(userDto);
    }

    @Test
    void findAllUsers() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/users").build().toUri();

        ResponseEntity<List<UserDto>> responseEntity = new ResponseEntity<>(userDtos, HttpStatus.OK);
        when(restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDto>>() {
        })).thenReturn(responseEntity);
        webUserService.findAllUsers();
        verify(restTemplate).exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDto>>() {
        });
    }

    @Test
    void countFavouriteByUserId() {
        uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/count/1").build().toUri();

        when(restTemplate.getForObject(uri, Long.class)).thenReturn(1L);
        webUserService.countFavouriteByUserId(1L);
        verify(restTemplate, Mockito.times(1)).getForObject(uri, Long.class);
    }
}