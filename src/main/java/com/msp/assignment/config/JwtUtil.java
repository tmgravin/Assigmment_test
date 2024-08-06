package com.msp.assignment.config;

//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "secret_key";

    public String generateToken(String username)
    {
    	System.out.println("token " + (System.currentTimeMillis() + 1000 * 60 * 60 * 10));
    	return null;
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
    }

    public String extractUsername(String token)
    {
    	return null;
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
    }

    public boolean validateToken(String token, String username)
    {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
    	return true;
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration()
//                .before(new Date());
    }
}