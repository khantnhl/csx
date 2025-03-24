package com.csx.app.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    /*
    * extract username (subject) from token.
    * @param JWT token
    * @return username stored in token
    * */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /*
    * extract specific claim from JWT token
    * @param Token and claimsResolver function to extract specific claim
    * @return extracted claim */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
    * extract all claims from JWT token
    * @param JWT Token
    * @return all claims from Token */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    /*
    * generate JWT token fo user + claim
    * @param userDetails
    * @param claims
    * @return JWT TOKEN
    * */
    public String generateToken(UserDetails userdetails, Map<String, Object> claims){
        return createToken(claims, userdetails);
    }


    /* creates JWT Token with claims, user, expiration date
    * @param claims to be included in the token
    * @param userDetails
    * @return JWT Token
    * */
    private String createToken(Map<String, Object> claims, UserDetails userdetails){
        return Jwts.builder().setClaims(claims)
                .setSubject(userdetails.getUsername())
                .claim("authorities", userdetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
    }

    /*
    * Validates if a Token is valid for a user
    * @param token
    * @param userDetails
    * @return true if token is valid
    */
    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

