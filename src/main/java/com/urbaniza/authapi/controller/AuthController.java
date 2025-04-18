package com.urbaniza.authapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urbaniza.authapi.model.Token;
import com.urbaniza.authapi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup") // Rota responsavel pelo registro do user
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

    @PostMapping("/signin") // Rota responsavel pelo login do user
    public ResponseEntity<Token> signin(@RequestBody Map<String, String> user){
        Token token = authService.signin(user.get("email"), user.get("password"));
        if(token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/check") // Rota que confere se o token de login gerado ainda é valido
    public	ResponseEntity<?> check(@RequestHeader String token) {
        Boolean isValid = authService.validade(token);
        return (isValid) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
