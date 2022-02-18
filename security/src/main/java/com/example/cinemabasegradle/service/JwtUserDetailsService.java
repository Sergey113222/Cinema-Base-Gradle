package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class JwtUserDetailsService {

    private static final String USER_DOES_NOT_EXIST = "User doesn't exist";
    private static final String BAD_CREDENTIALS = "Bad credentials";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User loadUserByEmail(String email, String password) {
        User user = userRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new IllegalArgumentException(USER_DOES_NOT_EXIST));
        log.info("In loadByEmail() - user with email: {} successfully loaded", email);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(BAD_CREDENTIALS);
        }
        return user;
    }
}
