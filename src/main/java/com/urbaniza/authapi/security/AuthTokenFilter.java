package com.urbaniza.authapi.security;

import com.urbaniza.authapi.service.UserDetailsServiceImpl;
import com.urbaniza.authapi.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsService;

  public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService){
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {

      String jwt = parseJwt(request);

      // logger.debug("AuthTokenFilter: Processando requisição para {}", request.getRequestURI());
      // logger.debug("AuthTokenFilter: Token JWT extraído: {}", jwt);

      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {

        String email = jwtUtils.getUserNameFromJwtToken(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // logger.debug("AuthTokenFilter: Usuário '{}' autenticado com sucesso com roles: {}", username, userDetails.getAuthorities());
      }
    } catch (Exception e) {
      logger.error("Não foi possível definir a autenticação do usuário: {}", e.getMessage());
    }

    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
