package com.example.cinemabasegradle.security;

import com.example.cinemabasegradle.model.JwtUser;
import com.example.cinemabasegradle.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getActive(),
                user.getUpdated(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())));
    }
}
