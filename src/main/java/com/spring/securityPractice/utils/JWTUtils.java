package com.spring.securityPractice.utils;

import com.spring.securityPractice.constants.AppConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

@Component
public class JWTUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final long EXPIRATION_TIME = 864_000_000;
    public static Boolean hasTokenExpired(String token){
        Claims claims = Jwts.parser().setSigningKey(AppConstants.TOKEN_SECRET).parseClaimsJws(token).getBody();
        Date tokenExpirationDate = claims.getExpiration();
        Date today = new Date();
        return tokenExpirationDate.before(today);
    }


    public static String generateToken(String username, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // Add the user's role as a claim
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, ALPHABET)
                .compact();
    }

    public static String generateUserID(int length){
        return generateRandomString(length);
    }

    private static String generateRandomString(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0;i<length;i++)
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        return new String(returnValue);
    }

    public static String extractUser(String token) {
        return Jwts.parser().setSigningKey(AppConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
    }
}
