package com.urbaniza.authapi.controller;

import com.urbaniza.authapi.dto.request.SigninRequestDTO;
import com.urbaniza.authapi.dto.request.SignupRequestDTO;
import com.urbaniza.authapi.dto.response.SigninResponseDTO;
import com.urbaniza.authapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urbaniza.authapi.model.Token;
import com.urbaniza.authapi.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup") // Rota responsavel pelo registro do user
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupRequestDTO) {

        try {
            authService.signup(signupRequestDTO.getEmail(), signupRequestDTO.getPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/signin") // Rota para login do usuário
    public ResponseEntity<?> signin(@RequestBody SigninRequestDTO signinRequestDTO) {
        Token token = authService.signin(signinRequestDTO.getEmail(), signinRequestDTO.getPassword());

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
        return ResponseEntity.ok(new SigninResponseDTO(token.getToken(), token.getExpTime()));
    }

    @PostMapping("/check") // Rota que confere se o token de login gerado ainda é valido
    public	ResponseEntity<?> check(@RequestHeader String token) {
        Boolean isValid = authService.validade(token);
        return (isValid) ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        boolean isVerified = authService.verifyEmail(token);
        if (isVerified) {
            return ResponseEntity.ok("E-mail verificado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
    }
}
