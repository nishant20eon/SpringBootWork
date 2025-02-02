package com.eon.security.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.eon.security.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * JWTService handles JWT token generation and secret key management.
 * It uses JSON Web Token (JWT) to securely authenticate users.
 * JWT structure and verification can be checked at https://jwt.io/.
 */
@Service
public class JWTService {

    // Secret key used for signing the JWT token
    private String secretKey;

    /**
     * Generates a JWT token for a given user.
     * This token contains encoded information about the user and has an expiration time.
     *
     * @param user The authenticated user object
     * @return A signed JWT token as a String
     */
    public String generateToken(User user) {
        // Custom claims can be added here (e.g., roles, permissions).
        Map<String, Object> claims = new HashMap<>();

        return Jwts
            .builder()
            .claims() 
            .add(claims)  // Adding custom claims (currently empty)
            .subject(user.getUserName())  // Setting the subject (username)
            .issuer("EON")  // Identifying the token issuer (EON)
            .issuedAt(new Date(System.currentTimeMillis()))  // Setting the issue timestamp
            .expiration(new Date(System.currentTimeMillis() + 60 * 10 * 1000))  // Expiry time (10 minutes)
            .and()
            .signWith(generateKey())  // Signing the token using the secret key
            .compact();  // Compacting the JWT to generate the final token
    }

    /**
     * Generates a SecretKey instance from a Base64-encoded key.
     * This key is used to sign and verify JWT tokens.
     *
     * @return SecretKey instance for secure JWT signing
     */
    private SecretKey generateKey() {
        // Decode the Base64-encoded secret key into bytes
        byte[] decodedKey = Decoders.BASE64.decode(getSecretKey());

        // Generate a HMAC-SHA key from the decoded bytes
        return Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Retrieves the Base64-encoded secret key used for signing JWT tokens.
     * In production, this key should be securely stored (e.g., in environment variables).
     *
     * @return A Base64-encoded secret key as a String
     */
    public String getSecretKey() {
        return secretKey = "gNC8PkE8qov8DUP9gyrwFU8ZHfI+Cb+sWtHIA/YAeZ8=";
    }

    public String extractUserName(String token) {
        // Extracts the subject (username) from the JWT token.
        // The subject is typically the user's identifier in the token claims.
    	String userName = extractClaims(token, Claims::getSubject);
        return userName; 
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
        // Retrieves the claims (payload) from the token.
        Claims claims = extractClaims(token); // Extracts all claims from the token.
        
        // Applies the provided function (claimResolver) to extract a specific claim.
        return claimResolver.apply(claims); 
    }

    private Claims extractClaims(String token) {
        // Parses the JWT token and retrieves the payload (claims).
        return Jwts
                    .parser()  // Initializes a JWT parser to process the token.
                    .verifyWith(generateKey())  // Uses a cryptographic key to verify the token's signature.
                    .build()    // Builds the parser instance.
                    .parseSignedClaims(token)  // Parses the signed JWT token to extract its claims.
                    .getPayload();    // Retrieves the claims (payload) from the token.
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token); // Extracts the username from the JWT token.
        
        // Checks if the extracted username matches the provided userDetails.
        // Also verifies that the token has not expired.
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        // Extracts the expiration date of the token and checks if it is before the current date.
        return extractExpiration(token).before(new Date()); 
        // `new Date()` creates an instance representing the current date/time.
        // The `.before()` method checks if the token's expiration date is in the past.
        // If true, the token has expired and should no longer be valid.
    }

    private Date extractExpiration(String token) {
        // Extracts the expiration date from the token's claims.
        return extractClaims(token, Claims::getExpiration);
    }

}
