/*
 * SCRIPT: Utils.java
 * AUTHOR: Wu (https://github.com/stupid-2020)
 * DATE  : 17-JAN-2022
 * NOTE  : 
 */
package com.example.demo.jwt;

import java.util.Date;

// If you use java-jwt
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

// If you use jjwt
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.SignatureException;
// import io.jsonwebtoken.UnsupportedJwtException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    @Value("${demo.app.jwtSecretKey}")
    private String jwtSecret;

    @Value("${demo.app.jwtExpirationSec}")
    private int jwtExpiration;

    public String generateJwtCookie(String token) {
        return ResponseCookie
            .from("token", token)
            .maxAge(jwtExpiration)
            .httpOnly(true)
            .path("/")
            .build()
            .toString();
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Use java-jwt
        String token = JWT
            .create()
            .withSubject((userPrincipal.getUsername()))
            .withIssuedAt(new Date())
            .withExpiresAt(new Date((new Date()).getTime() + (jwtExpiration * 1000)))
            .sign(Algorithm.HMAC512(jwtSecret));
        
        return token;

        // If you use jjwt
        // return Jwts
        //     .builder()
        //     .setSubject((userPrincipal.getUsername()))
        //     .setIssuedAt(new Date())
        //     .setExpiration(new Date((new Date()).getTime() + (jwtExpiration * 1000)))
        //     .signWith(SignatureAlgorithm.HS512, jwtSecret)
        //     .compact();
    }

    public String getUserNameFromJwtToken(String authToken) {
        JWTVerifier verifier = JWT
            .require(Algorithm.HMAC512(jwtSecret))
            .build();
        DecodedJWT jwt = verifier.verify(authToken);

        return jwt.getSubject();

        // If you use jjwt
        // return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JWTVerifier verifier = JWT
                .require(Algorithm.HMAC512(jwtSecret))
                .build();
            DecodedJWT jwt = verifier.verify(authToken);

            // Note: the following line is used to suppress VS Code warning
            jwt.getSubject();

            return true;
        } catch (SignatureVerificationException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            logger.error("JWT Secret: {}", jwtSecret);
        } catch (TokenExpiredException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (JWTVerificationException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }

        // If you use jjwt
        // try {
        //     Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        //     return true;
        // } catch (SignatureException e) {
        //     logger.error("Invalid JWT signature: {}", e.getMessage());
        // } catch (MalformedJwtException e) {
        //     logger.error("Invalid JWT token: {}", e.getMessage());
        // } catch (ExpiredJwtException e) {
        //     logger.error("JWT token is expired: {}", e.getMessage());
        // } catch (UnsupportedJwtException e) {
        //     logger.error("JWT token is unsupported: {}", e.getMessage());
        // } catch (IllegalArgumentException e) {
        //     logger.error("JWT claims string is empty: {}", e.getMessage());
        // }

        return false;
    }
}
