package com.urbaniza.authapi.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * Este componente é um ponto de entrada para autenticação. É chamado quando um utilizador
 * não autenticado tenta aceder a um recurso que requer autenticação.
 * O seu principal propósito é retornar uma resposta HTTP 401 (Não Autorizado)
 * com uma mensagem de erro apropriada.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthEntryPointJwt(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Este método é chamado quando uma autenticação falha.
     *
     * @param request       O objeto HttpServletRequest que contém detalhes sobre a requisição HTTP.
     * @param response      O objeto HttpServletResponse que pode ser usado para construir a resposta HTTP.
     * @param authException A exceção AuthenticationException que foi lançada devido à falha na autenticação.
     * @throws IOException      Se ocorrer um erro de E/S durante o manuseio da resposta.
     * @throws ServletException Se ocorrer um erro específico do servlet.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // Log the error for debugging and monitoring purposes on the server.
        logger.error("Authentication Exception Details: ", authException);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now().toString());
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        
        String userFriendlyMessage = "Acesso não autorizado. Verifique suas credenciais ou token de autenticação.";
        errorDetails.put("message", userFriendlyMessage);
        errorDetails.put("path", request.getRequestURI());

        objectMapper.writeValue(response.getWriter(), errorDetails);
    }
}
