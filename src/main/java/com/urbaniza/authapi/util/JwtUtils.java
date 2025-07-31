package com.urbaniza.authapi.util;


import com.urbaniza.authapi.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Random;
import java.util.function.Function;

@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${urbaniza.app.jwtSecret}")
  private String jwtSecret;
  @Value("${urbaniza.app.jwtAccessExpirationMs}")
  private int jwtAccessExpirationMs;
  @Value("${urbaniza.app.jwtRefreshExpirationMs}")
  private int jwtRefreshExpirationMs;

  private Key key;

  @PostConstruct
  public void init(){
    this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  // Extract the email from the token
  public String getUserNameFromJwtToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  // Extracts the expiration date of the token
  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  // Extracts the role(s) from the claim "role" of the JWT token.
  public String getRoleFromJwtToken(String token) {
    Claims claims = getAllClaimsFromToken(token);
    return claims.get("role", String.class);
  }

  // Generic function to extract specific information (claim) from the token.
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  // Extracts all claims (information) from the body of the JWT token.
  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parserBuilder()
      .setSigningKey(this.key)
      .build()
      .parseClaimsJws(token)
      .getBody();
  }

  // Validates the JWT token (signature, expiration, format).
  public boolean validateJwtToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(this.key)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Malformed JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("Expired JWT Token: {}", e.getMessage());
    } catch (SignatureException e) {
      logger.error("Invalid JWT signature: {}", e.getMessage());
    } catch (JwtException e) {
      logger.error("Token JWT expired: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty or null: {}", e.getMessage());
    }
    return false;
  }

  public String generateAccessToken(UserDetails userDetails) {

    String role = userDetails.getAuthorities().stream()
      .findFirst()
      .map(GrantedAuthority::getAuthority)
      .orElseThrow(() -> new IllegalStateException("User " + userDetails.getUsername() + " does not have defined roles.")); // valor padrão de segurança

    Long userId = null;
    if (userDetails instanceof com.urbaniza.authapi.model.User) {
      userId = ((com.urbaniza.authapi.model.User) userDetails).getId();
    }

    Long departmentId = null;
    if (userDetails instanceof com.urbaniza.authapi.model.User) {
      com.urbaniza.authapi.model.User user = (com.urbaniza.authapi.model.User) userDetails;
      if (user.getDepartmentId() != null) {
        departmentId = user.getDepartmentId();
      }
    }


    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .claim("role", role)
      .claim("userId", userId)
      .claim("departmentId", departmentId)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtAccessExpirationMs))
      .signWith(this.key, SignatureAlgorithm.HS512)
      .compact();
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return Jwts.builder()
      .setSubject(userDetails.getUsername())
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
      .signWith(this.key, SignatureAlgorithm.HS512)
      .compact();
  }

  public String generateConfirmEmailToken(String emailToConfirm) {
    long twoHundredYearsInMillis = 6311520000000L;

    return Jwts.builder()
        .setSubject(emailToConfirm)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + twoHundredYearsInMillis))
        .signWith(this.key, SignatureAlgorithm.HS512)
        .compact();
  }

  public String generatePasswordRecoveryToken(String random) {
    long fiveMinutesInMillis = 300000L;

    return Jwts.builder()
            .setSubject(random)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + fiveMinutesInMillis))
            .signWith(this.key, SignatureAlgorithm.HS512)
            .compact();
  }

  // Checks if the JWT token is expired.
  public boolean isTokenExpired(String token) {
    try {
      return getExpirationDateFromToken(token).before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    } catch (JwtException e) {
      logger.warn("Error checking token expiration, treating it as invalid/expired: {}", e.getMessage());
      return true;
    }

  }
}

