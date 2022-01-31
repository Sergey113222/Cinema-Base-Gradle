package com.example.cinemabasegradle.security;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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
        return new UsernamePasswordAuthenticationToken(this.getUsername(token), "", getAuthorities(token));
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] chunks = token.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonObject = new JSONObject(payload);
        String role = jsonObject.getString("role");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION);
        if (bearerToken == null && !bearerToken.startsWith(BEARER)) {
            throw new IllegalArgumentException(MESSAGE_EMPTY);
        }
        return bearerToken.substring(7);
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

        if (claims.getBody().getExpiration().before(new Date())) {
            return false;
        }
        return true;
    }
}
