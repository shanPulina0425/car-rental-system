package com.carrental.car_rental.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    // 1. Hotel Manager ge Rahas Seel eka (Secret Key)
    // Meka godak digai, kauruth dan naha. (Aththa project ekaka meka properties file ekata danawa)
    private static final String SECRET_KEY = "MageCarRentalProjectEkeLokuRahasKeyEkaKauruthDanneNaha123456789";

    // 2. Keycard eka wada karana kalaya (Milisthappara walin = Paya 24)
    private static final long EXPIRATION_TIME = 86400000;

    // Seel eka hadana machine eke (Algorithm) kalla
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ==========================================
    // WADA 1: Aluth Keycard ekak Print Kireema
    // ==========================================
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Keycard eke aithi karaya (Email eka)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Print karapu welawa
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expire wena welawa
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Ara Rahas Seel eka gahanawa
                .compact(); // Okkoma guli karala loku String ekak (Token eka) widiyata denawa
    }

    // ==========================================
    // WADA 2: Keycard eken Nama (Email) Kiyeweema
    // ==========================================
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ==========================================
    // WADA 3: Keycard eka Boru ekakda kiyala Balima (Validation)
    // ==========================================
    public boolean isTokenValid(String token, String userEmail) {
        final String emailInToken = extractEmail(token);
        return (emailInToken.equals(userEmail)) && !isTokenExpired(token);
    }

    // ---- Palliha thiyenne udaw wena podi wada (Helpers) ----

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Seel eka dila ekanma token eka open karagannawa
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}