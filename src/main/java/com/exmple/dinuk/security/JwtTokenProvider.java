package com.exmple.dinuk.security;

import  com.exmple.dinuk.exception.CustomExceptions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    //used to sign the jwt token

    @Value("${app.jwt-expiration-in-ms}")
    private int jwtExpirationInMs;

    @Value("${app.jwt-refresh-expiration-in-ms}")
    private int jwtRefreshExpirationInMs;

    private Key getSigningKey() {
        // Decode the base64 encoded secret key
        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        // Generate and return the signing key
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate an access token using the Authentication object
    public String generateToken(Authentication authentication) {
        // Call the private generateToken method with the username and expiration time
        return generateToken(authentication.getName(), jwtExpirationInMs);
    }

    // Generate a refresh token using the username
    public String generateRefreshToken(String username) {
        // Call the private generateToken method with the username and refresh token expiration time
        return generateToken(username, jwtRefreshExpirationInMs);
    }

    // Private method to generate a token with the given subject and expiration time
    private String generateToken(String subject, int expirationTime) {
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + expirationTime);
        Key key = getSigningKey();

        // Build and return the JWT token
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }


    // Get username from token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Validate the token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new CustomExceptions.InvalidJwtTokenException("Expired or invalid JWT token", e);
        }
    }
}
