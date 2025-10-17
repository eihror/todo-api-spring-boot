package br.com.eihror.todo_list.core.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Map;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    private Key key() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails user, Map<String, ?> extraClaims) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(key(), SignatureAlgorithm.HS256);

        if (extraClaims != null) {
            extraClaims.forEach((k,v) -> {
                if (!"sub".equals(k)) builder.claim(k, v);
            });
        }

        return builder.compact();
    }

    public String generateToken(UserDetails user) {
        return generateToken(user, Map.of());
    }

    public String extractUsername(String token) {
        return parse(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return username.equals(user.getUsername());
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token);
    }
}
