package com.ardaslegends.service.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for handling JWT (JSON Web Token) operations such as generating tokens, extracting claims, and validating tokens.
 */
@Slf4j
@Service
public class JwtUtil {

    /**
     * The secret key used for signing the JWT.
     */
    @Value("${ardaslegends.auth.jwt.secret}")
    private String secretKey;

    /**
     * Generates a JWT token for the given Discord ID and access token with a specified expiration time.
     *
     * @param discordId          The Discord ID of the user.
     * @param discordAccessToken The access token received from Discord.
     * @param expiresIn          The duration in seconds for which the token is valid.
     * @return The generated JWT token.
     */
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

    /**
     * Extracts the Discord ID from the given JWT token.
     *
     * @param token The JWT token.
     * @return The Discord ID.
     */
    public String extractDiscordIdFromJWT(String token) {
        return extractClaim(token, claims -> claims.get("discordId", String.class));
    }

    /**
     * Extracts a specific claim from the given JWT token using the provided claims resolver function.
     *
     * @param token          The JWT token.
     * @param claimsResolver The function to resolve the claim from the token.
     * @param <T>            The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token The JWT token.
     * @return The claims extracted from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Validates the given JWT token.
     *
     * @param token The JWT token.
     * @return true if the token is valid, false otherwise.
     * @throws ExpiredJwtException      if the token has expired.
     * @throws UnsupportedJwtException  if the token is unsupported.
     * @throws MalformedJwtException    if the token is malformed.
     * @throws SignatureException       if the token has an invalid signature.
     * @throws IllegalArgumentException if the token is empty or null.
     */
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