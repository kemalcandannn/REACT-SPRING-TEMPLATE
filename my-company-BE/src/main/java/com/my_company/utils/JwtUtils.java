package com.my_company.utils;

import com.my_company.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public final class JwtUtils {
    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    private JwtUtils() {
        throw new UnsupportedOperationException(ApplicationConstants.UTILITY_CLASS);
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 saat
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public static boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().before(new Date());
    }
}
