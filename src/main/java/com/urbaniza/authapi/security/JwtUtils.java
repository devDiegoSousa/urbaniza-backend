package com.urbaniza.authapi.security;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.ExpiredJwtException;

/**
 * Esta classe utilitária fornece métodos para gerar, validar e analisar tokens JWT.
 */
@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${urbaniza.app.jwtSecret}") // Injeta a chave secreta do application.properties
    private String jwtSecret;

    @Value("${urbaniza.app.jwtExpirationMs}") // Injeta o tempo de expiração do token
    private int jwtExpirationMs;

    /**
     * Gera um token JWT para um utilizador autenticado.
     *
     * @param authentication O objeto Authentication do Spring Security contendo os detalhes do utilizador.
     * @return O token JWT gerado.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername())) // Define o nome de utilizador como o "subject" do token
                .setIssuedAt(new Date()) // Define a data de emissão do token
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Define a data de expiração
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Assina o token com o algoritmo HS512 e a chave secreta
                .compact(); // Converte o token para uma string compacta e segura para transmissão
    }

    /**
     * Extrai o nome de utilizador (ou email) do token JWT.
     *
     * @param token O token JWT.
     * @return O nome de utilizador extraído do token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Valida um token JWT.
     *
     * @param token O token JWT a ser validado.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Assinatura JWT inválida: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Token JWT expirado: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Token JWT não suportado: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("String JWT está vazia: {}", e.getMessage());
        }

        return false;
    }
}

