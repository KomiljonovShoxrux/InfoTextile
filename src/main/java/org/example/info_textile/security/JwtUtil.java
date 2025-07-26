package org.example.info_textile.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET = "owieurqpowieurpqwoierupqwoieurpwoqeiuroweurwoeiuriowueoruiowueoiruiowueiruerutyturytyrutyurtyurtuyruyrtyyrtyyrytryytry";
    private final Long EXPIRATION = 1000 * 60 * 60 * 5L; // 5 soat

    private SecretKey key;

    @PostConstruct
    public void init() {
        // secret ni to'g'ri formatga keltirish (min 256 bit = 32 byte)
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String createToken(String email, String role, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("name", name);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, key) // <-- SHU YERDA TO‘G‘RILANGAN
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractName(String token) {
        return getClaims(token).get("name", String.class);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }
}
