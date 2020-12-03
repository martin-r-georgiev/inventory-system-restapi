package org.martin.inventory.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.martin.inventory.UserRole;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JWTUtil {

    private final static String SECRET_KEY = "martin-secret-key";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UserRole extractRole(String token) {
        final Claims claims = extractAllClaims(token);
        return UserRole.valueOf((String) claims.get("role"));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        final String subject = extractUsername(token);
        return (subject.equals(username) && !isTokenExpired(token));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(String username, UserRole role) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, role.toString());
    }

    private String createToken(Map<String, Object> claims, String subject, String role) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // Expires after a week
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
}
