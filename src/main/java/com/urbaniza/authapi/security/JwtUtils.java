package com.urbaniza.authapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    // Método para extrair o e-mail do token
    public String getUserNameFromJwtToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Método para extrair a data de expiração do token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Método para extrair qualquer claim do token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Método para extrair todos os claims do token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    // Método para validar o token
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Assinatura JWT inválida: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT não suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("String JWT está vazia: {}", e.getMessage());
        }
        return false;
    }

    // Método para gerar o token JWT de acesso
    public String generateAccessToken(UserDetails userDetails) {

        System.out.println("Novo token");
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtAccessExpirationMs)) // Expiração do token de acesso
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Método para gerar o token JWT de refresh
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs)) // Expiração do token de refresh
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Método para verificar se o token está expirado
    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
}
