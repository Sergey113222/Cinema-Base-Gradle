package com.example.cinemabasegradle.controller;


import com.example.cinemabasegradle.dto.AuthenticationRequestDto;
import com.example.cinemabasegradle.dto.AuthenticationResponseDto;
import com.example.cinemabasegradle.exception.JwtAuthenticationException;
import com.example.cinemabasegradle.model.JwtUser;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth")
public class AuthenticationRestController {

    private final static String MESSAGE = "Invalid email or password";
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/login")
    public AuthenticationResponseDto login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            String password = requestDto.getPassword();
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            JwtUser principal = (JwtUser) authenticate.getPrincipal();

            List<? extends GrantedAuthority> simpleGrantedAuthorities = new ArrayList<>(principal.getAuthorities());
            Role role = Role.valueOf(simpleGrantedAuthorities.get(0).toString());

            String token = jwtTokenProvider.createToken(email, role);

            return new AuthenticationResponseDto(email, token);
        } catch (JwtAuthenticationException e) {
            throw new BadCredentialsException(MESSAGE);
        }
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
