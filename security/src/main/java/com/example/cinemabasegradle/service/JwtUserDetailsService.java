package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.model.JwtUser;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.security.JwtUserFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class JwtUserDetailsService implements UserDetailsService {

    private final static String MESSAGE = "User doesn't exist";
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException(MESSAGE));
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("In loadByEmail() - user with email: {} successfully loaded", email);
        return jwtUser;
    }
}
