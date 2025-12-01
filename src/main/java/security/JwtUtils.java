//package security;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import config.JwtProperties;
//
//import java.security.Key;
//import java.util.Date;
//
//
//@Component
//public class JwtUtils {
//    
//    @Autowired
//    private JwtProperties jwtProperties;
//    
//    private Key getSigningKey() {
//        // Make sure the secret is long enough for HS256 (at least 32 characters)
//        String secret = jwtProperties.getJwtSecret();
//        if (secret.length() < 32) {
//            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
//        }
//        return Keys.hmacShaKeyFor(secret.getBytes());
//    }
//    
//    public String generateJwtToken(org.springframework.security.core.Authentication authentication) {
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//        
//        return Jwts.builder()
//                .setSubject(userPrincipal.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getJwtExpirationMilliseconds()))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//    
//    public String getEmailFromJwtToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//    
//    public boolean validateJwtToken(String authToken) {
//        try {
//            Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(authToken);
//            return true;
//        } catch (JwtException e) {
//            System.err.println("JWT validation error: " + e.getMessage());
//        }
//        return false;
//    }
//
//    public String generateTokenFromEmail(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getJwtExpirationMilliseconds()))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//}
package security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import config.JwtProperties;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    
    @Autowired
    private JwtProperties jwtProperties;
    
    private Key getSigningKey() {
        String secret = jwtProperties.getJwtSecret();
        
        // Handle null or empty secret
        if (secret == null || secret.trim().isEmpty()) {
            System.err.println("❌ JWT secret is null or empty! Using fallback for development.");
            secret = "fallback-secret-key-for-development-only-change-in-production";
        }
        
        // For development - ensure minimum length
        if (secret.length() < 32) {
            System.out.println("⚠️  JWT secret is too short. Padding for development use only.");
            // Pad the secret to meet minimum length requirement
            StringBuilder paddedSecret = new StringBuilder(secret);
            while (paddedSecret.length() < 32) {
                paddedSecret.append("0");
            }
            secret = paddedSecret.toString();
        }
        
        System.out.println("✅ Using JWT secret (first 10 chars): " + secret.substring(0, Math.min(10, secret.length())) + "...");
        
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String generateJwtToken(org.springframework.security.core.Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getJwtExpirationMilliseconds()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String generateTokenFromEmail(String email) {
        System.out.println("🔐 Generating token for email: " + email);
        
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtProperties.getJwtExpirationMilliseconds()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        
        System.out.println("✅ Token generated successfully");
        return token;
    }
    
    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            System.err.println("JWT validation error: " + e.getMessage());
        }
        return false;
    }
}