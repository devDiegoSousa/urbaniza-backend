package com.urbaniza.authapi.security;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;  // Importar HttpStatus
import org.springframework.http.MediaType; // Importar MediaType

/**
 * Este componente é um ponto de entrada para autenticação. É chamado quando um utilizador
 * não autenticado tenta aceder a um recurso que requer autenticação.
 * O seu principal propósito é retornar uma resposta HTTP 401 (Não Autorizado)
 * com uma mensagem de erro apropriada.
 */
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

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
        // Regista o erro
        logger.error("Resposta não autorizada: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().println("{ \"error\": \"" + authException.getMessage() + "\" }");
    }
}
