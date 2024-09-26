package com.ardaslegends.service.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtil {
    @Value("${ardaslegends.auth.jwt.secret}")
    private String secretKey;

    public String generateToken(String discordId, String discordAccessToken, long expiresIn) {
        log.debug("Generating token for discordAccessToken: {}", discordId);
        log.debug("Secret key: {}", secretKey);
        return Jwts.builder()
                .claim("discordId", discordId)
                .setId(discordAccessToken)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresIn * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String extractDiscordAccessTokenFromJWT(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getId();
    }

    public String extractDiscordIdFromJWT(String token) {
        return extractClaim(token, claims -> claims.get("discordId", String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
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
