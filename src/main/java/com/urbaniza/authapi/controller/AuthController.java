package com.urbaniza.authapi.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.urbaniza.authapi.model.Token;
import com.urbaniza.authapi.service.AuthService;
import com.urbaniza.authapi.security.JwtUtils; // Importa JwtUtils

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup") // Rota responsável pelo registro do utilizador
    public ResponseEntity<?> signup(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        String password = user.get("password");
        try {
            authService.signup(email, password);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/signin") // Rota responsável pelo login do utilizador
    public ResponseEntity<Token> signin(@RequestBody Map<String, String> user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.get("email"), user.get("password")));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Token token = new Token();
        token.setToken(jwt);
        return ResponseEntity.ok(token);
    }
}