package com.example.taskmanager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // Secret key for signing the JWT, more secure than a simple string
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Use a Key object for signing

    // Method to generate JWT token
    public String generateToken(UserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 1 day expiration time

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)  // Sign with the Key object
                .compact();
    }

    // Method to extract the username from the JWT token
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()  // Update to use parserBuilder in newer versions
                .setSigningKey(secretKey)  // Use the Key object for verification
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // Get the username from the "subject" claim
    }

    // Method to validate the token
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Method to check if the token has expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()  // Update to use parserBuilder in newer versions
                .setSigningKey(secretKey)  // Use the Key object for verification
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    // Method to extract the token from the Authorization header in the HTTP request
    public String resolveToken(jakarta.servlet.http.HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Return token by removing "Bearer " prefix
        }
        return null; // Return null if token is not found
    }
}