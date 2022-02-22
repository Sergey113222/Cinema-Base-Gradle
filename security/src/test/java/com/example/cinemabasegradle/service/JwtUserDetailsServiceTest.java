package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("jdbc")
class JwtUserDetailsServiceTest {

    private JwtUserDetailsService jwtUserDetailsService;
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private User user;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        jwtUserDetailsService = new JwtUserDetailsService(userRepository, bCryptPasswordEncoder);
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
    void loadUserByEmail() {
        when(userRepository.findByEmailAndActiveTrue(user.getEmail())).thenReturn(Optional.ofNullable(user));
        when(bCryptPasswordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);
        User userLoaded = jwtUserDetailsService.loadUserByEmail(this.user.getEmail(), this.user.getPassword());
        verify(userRepository).findByEmailAndActiveTrue(any());
        assertEquals(user.getUsername(), userLoaded.getUsername());
        assertEquals(user.getEmail(), userLoaded.getEmail());
    }

    @Test
    void loadUserByEmailThrowIllegalArgumentException() {
        when(userRepository.findByEmailAndActiveTrue("test@mail.ru")).thenReturn(Optional.ofNullable(user));
        when(bCryptPasswordEncoder.matches("password", "password")).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> jwtUserDetailsService.loadUserByEmail("test@mail.ru", "password"));
    }
}