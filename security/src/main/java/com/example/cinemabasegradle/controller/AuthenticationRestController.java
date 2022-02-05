package com.example.cinemabasegradle.controller;


import com.example.cinemabasegradle.dto.AuthenticationRequestDto;
import com.example.cinemabasegradle.dto.AuthenticationResponseDto;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.service.JwtTokenProvider;
import com.example.cinemabasegradle.service.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping
    public AuthenticationResponseDto auth(@Valid @RequestBody AuthenticationRequestDto requestDto) {

        User user = jwtUserDetailsService.loadUserByEmail(requestDto.getEmail(), requestDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getId(), user.getRole());

        return new AuthenticationResponseDto(requestDto.getEmail(), token);
    }
}
