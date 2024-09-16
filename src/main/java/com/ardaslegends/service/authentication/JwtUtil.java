package com.ardaslegends.service.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    @Value("${ardaslegends.auth.jwt.secret}")
    private static String secretKey;

    @Value("${ardaslegends.auth.jwt.expiration-time}")
    private static long jwtExpiration;

    public String generateToken(String discordAccessToken, long expiresIn) {
        return Jwts.builder()
                .setSubject(discordAccessToken)
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    public String extractDiscordAccessTokenFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
