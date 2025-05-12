package com.urbaniza.authapi.controller;

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
import com.urbaniza.authapi.dto.auth.SigninRequestDTO;
import com.urbaniza.authapi.dto.auth.SigninResponseDTO;
import com.urbaniza.authapi.dto.auth.SignupRequestDTO;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.security.JwtUtils;
import com.urbaniza.authapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam; //Importa oRequestParam
import com.urbaniza.authapi.enums.UserRole; //Importa UserRole

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        try {
            authService.signup(signupRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDTO> signin(@Valid @RequestBody SigninRequestDTO signinRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        long expTime = jwtUtils.getExpirationDateFromToken(jwt).getTime();

        SigninResponseDTO response = new SigninResponseDTO();
        response.setToken(jwt);
        response.setExpTime(expTime);
        return ResponseEntity.ok(response);
    }
}

