package com.urbaniza.authapi.security;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.urbaniza.authapi.service.UserDetailsServiceImpl;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Filtro para interceptar e validar o token JWT em cada requisição.
 * Este filtro garante que cada requisição (exceto para rotas de login/registro)
 * contenha um token JWT válido no cabeçalho de autorização.
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  private static final List<String> PUBLIC_URLS = List.of(
      "/auth/signup",
      "/auth/signin"
  );

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    System.out.println("Path atual: " + request.getRequestURI());
    return PUBLIC_URLS.stream().anyMatch(path::startsWith);
  }

  /**
   * Intercepta cada requisição para validar o token JWT.
   *
   * @param request     Objeto HttpServletRequest contendo detalhes da requisição.
   * @param response    Objeto HttpServletResponse para enviar respostas.
   * @param filterChain Cadeia de filtros para continuar o processamento da requisição.
   * @throws ServletException Se ocorrer um erro durante o processamento do filtro.
   * @throws IOException      Se ocorrer um erro de E/S.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      System.out.println("AuthTokenFilter");

      String jwt = parseJwt(request);
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 5. Define a autenticação no contexto de segurança do Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      // Logar o erro para diagnóstico
      logger.error("Não foi possível definir a autenticação do usuário: {}", e);
    }

    // 6. Continua o processamento da requisição, passando para o próximo filtro na cadeia
    filterChain.doFilter(request, response);
  }

  /**
   * Extrai o token JWT do cabeçalho "Authorization" da requisição.
   * O cabeçalho deve estar no formato "Bearer <token>".
   *
   * @param request Objeto HttpServletRequest contendo a requisição.
   * @return O token JWT, ou null se o cabeçalho estiver ausente ou inválido.
   */
  private String parseJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7); // Remove o "Bearer " para obter apenas o token
    }
    return null;
  }
}

