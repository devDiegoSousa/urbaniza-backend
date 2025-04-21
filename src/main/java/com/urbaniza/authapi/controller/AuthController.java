package com.urbaniza.authapi.controller;

import java.util.Map;

import com.urbaniza.authapi.dto.request.AuthRequestDTO;
import com.urbaniza.authapi.dto.response.AuthResponseDTO;
import com.urbaniza.authapi.model.User;
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
    public ResponseEntity<?> signup(@RequestBody AuthRequestDTO request) {

        try {
            authService.signup(request.getEmail(), request.getPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/signin") // Rota para login do usuário
    public ResponseEntity<?> signin(@RequestBody AuthRequestDTO request) {
        Token token = authService.signin(request.getEmail(), request.getPassword());

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }

        User user = token.getUser(); // O token tem o user dentro dele

        AuthResponseDTO responseDTO = new AuthResponseDTO(
                token.getToken(),
                user.getEmail(),

        );

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/check") // Rota que confere se o token de login gerado ainda é valido
    public	ResponseEntity<?> check(@RequestHeader String token) {
        Boolean isValid = authService.validade(token);
        return (isValid) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }
}
