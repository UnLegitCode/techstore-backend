package ru.unlegit.techstore.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.unlegit.techstore.model.JwtClaims;
import ru.unlegit.techstore.model.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey createKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusMillis(expirationMs)))
                .signWith(createKey())
                .compact();
    }

    public JwtClaims parseToken(String token) throws JwtException, IllegalArgumentException {
        var payload = Jwts.parser()
                .verifyWith(createKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new JwtClaims(
                payload.get("userId", Integer.class),
                payload.get("role", String.class)
        );
    }
}