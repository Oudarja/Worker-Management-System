/*JWT (JSON Web Token) is a secure way to verify and share information between two parties — usually
 a client (e.g., browser, mobile app) and a server.
 * The given code defines a utility class JWTUtils in a Spring application for handling JSON
 *  Web Tokens (JWTs). This class performs tasks like generating, validating, and extracting 
 * data from JWTs, essential for handling authentication and authorization
 * 
 * 
 * Structure of JWT:
  A JWT consists of three parts:
  1)Header: Contains metadata, such as the signing algorithm (e.g., HMAC-SHA256).
  2)Payload: Contains claims, such as user details (username, roles, etc.).
  3)Signature: Created by signing the encoded header and payload using a secret key, ensuring
  that the token cannot be tampered with.

  Authentication Flow using JWT:

 1)Client login: The client sends credentials (username/password) to the server.
 2)JWT Generation: After validation, the server generates a JWT for the user, embedding necessary
 claims (e.g., username) in the payload and signing it.
 3)Client stores JWT: The client stores the token (e.g., in local storage or cookies) and uses it 
 for subsequent requests.
 4)Token validation: The server verifies the token's signature and extracts user information for access control.
If the user logs out, the token becomes "inactive." If they log in again, the server generates a fresh JWT and sends
it to the client.Verification happens on every request to a protected resource.

The server extracts the JWT from the Authorization header and validates it to:
1)Ensure the signature is valid (hasn't been tampered with).
2)Confirm that the token has not expired.
3)Extract the claims (e.g., username) to decide if the user is authorized to perform the action.

Once the JWT is generated after login:
The client stores it (in memory, local storage, cookies, etc.).
The server only needs to validate the JWT on subsequent requests.
If the user logs out, the token becomes "inactive." If they log in
again, the server generates a fresh JWT and sends it to the client.

*/

package com.example.UserManagementSystem.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;

import javax.crypto.spec.*;

@Component
public class JWTUtils {

    private final SecretKey Key;
    // one day in milisecond
    private static final long EXPIRATION_TIME = 86400000;

    public JWTUtils() {
        /*
         * A secret key is created using Base64 decoding of the secreteString.
         * The SecretKey object is used to sign tokens, ensuring their integrity
         */

        String secreteString = "OHNhbnFldzc5ODIzdTMyODEyOTgzMkpESkROTk5ORFNOTldDRUROTkNOSk5KU0NWRDMyNzc4ODg4ODg4ODg4UkVGTkpOQ1ZTREpOQ05KUVVRVXF1aXF1aVVVVQ==";
        byte[] keyBytes = Base64.getDecoder().decode(secreteString.getBytes(StandardCharsets.UTF_8));

        this.Key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // This method generates a JWT for authentication using the io.jsonwebtoken.Jwts
    // library
    // A UserDetails object (from Spring Security), which provides the username
    // (userDetails.getUsername()).
    /*
     * JWT Construction: A token is built with:
     * Subject: User’s username.
     * Issued Time: Token issuance time.
     * Expiration Time: 24 hours after issuance (EXPIRATION_TIME = 86400000ms).
     * Signature: Ensures the token was signed by the trusted server (using the
     * secret key).
     */

    public String generateToken(UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }
    /*
     * claims: A HashMap of custom claims to include in the refresh token payload.
     * userDetails: Represents the user (email/username) whose refresh token is
     * being generated.
     */

    public String genrateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {

        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    // Extracts the username from the token by retrieving the subject claim
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Uses Jwts.parser() with the signing key to parse the token, and then applies
    // the provided function (Claims::getSubject, Claims::getExpiration, etc.) to
    // extract the desired claim.

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction
                .apply(Jwts.parser()
                        .verifyWith(Key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload());
    }

    // Extracts the username from the token and matches it with the user's username.
    // Ensures the token is not expired.
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

}

/*
 * 1. What Happens if a User Sends an Expired JWT?
 * When the client makes a request with an expired JWT, the server will:
 * 
 * Attempt to validate the token.
 * Check the token's expiration claim (exp).
 * Reject the token since it's expired.
 * The server typically responds with an HTTP status code:
 * 
 * 401 Unauthorized: Indicates the token is invalid due to expiration.
 * 2. What Does the Client Need to Do?
 * If a token expires, the user must authenticate again, either by:
 * 
 * Using a Refresh Token:
 * 
 * If your app uses Refresh Tokens:
 * Alongside the JWT, the server issues a Refresh Token (with a longer
 * expiration time, e.g., 7 days or 30 days).
 * When the JWT expires, the client sends the refresh token to the server to
 * request a new JWT.
 * The server validates the refresh token and issues a new JWT.
 * This avoids requiring the user to log in again frequently.
 * Re-login:
 * 
 * If your app doesn't use refresh tokens, the user must log in again (send
 * their credentials to obtain a new JWT).
 * 
 * 
 * 
 */