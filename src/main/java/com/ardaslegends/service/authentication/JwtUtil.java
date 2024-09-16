package com.ardaslegends.service.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {
    @Value("${ardaslegends.auth.jwt.secret}")
    private static String secretKey;

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

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // Invalid signature/claims
            throw new SignatureException("Invalid signature/claims");
        } catch (ExpiredJwtException ex) {
            // Expired token
            throw new ExpiredJwtException(null, null, "Token has expired");
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token
            throw new UnsupportedJwtException("Unsupported JWT token");
        } catch (MalformedJwtException ex) {
            // Malformed JWT token
            throw new MalformedJwtException("Malformed JWT token");
        } catch (IllegalArgumentException ex) {
            // JWT token is empty
            throw new IllegalArgumentException("JWT token is empty");
        }
    }

}
