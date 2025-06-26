package com.beanFactory.toursTravels.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String secret = Base64.getEncoder()
            .encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

       return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 1000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        Key keys = Keys.hmacShaKeyFor(secret.getBytes());
        System.out.println(secret);
        return keys;
    }
}
