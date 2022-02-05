package com.example.cinemabasegradle.security;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2"})
public class JwtTokenProvider {

    private final static String MESSAGE = "JWT token is expired or invalid";
    private final static String MESSAGE_EMPTY = "JWT token is empty or incorrect";
    private final static String AUTHORIZATION = "Authorization";
    private final static String BEARER = "Bearer ";

    @Value("${jwt.token.secret}")
    private String secret;

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }


    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(this.getId(token), "", getAuthorities(token));
    }

    public Long getId(String token) {
        return Long.parseLong(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("jti", String.class));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        String role = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role", String.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        if (bearerToken == null && !bearerToken.startsWith(BEARER)) {
            throw new BadCredentialsException(MESSAGE);
        }
        return bearerToken.substring(7);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException(MESSAGE);
        }
    }
}
