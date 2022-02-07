package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.model.Role;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long validityMilliseconds;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(Long id, List<Role> roles) {
        Claims claims = Jwts.claims().setId(id.toString());
        List<String> roleStringList = new ArrayList<>();
        roles.forEach(role -> roleStringList.add(role.getName()));
        claims.put("roles", roleStringList);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
