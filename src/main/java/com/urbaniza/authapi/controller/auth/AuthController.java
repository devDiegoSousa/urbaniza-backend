package com.urbaniza.authapi.controller.auth;

import com.urbaniza.authapi.dto.auth.refresh.RefreshTokenResponseDTO;
import com.urbaniza.authapi.dto.auth.signin.SigninRequestDTO;
import com.urbaniza.authapi.dto.auth.signin.SigninResponseDTO;
import com.urbaniza.authapi.dto.auth.signup.SignupRequestDTO;
import com.urbaniza.authapi.dto.auth.refresh.RefreshTokenRequestDTO;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.UserRepository;
import com.urbaniza.authapi.util.JwtUtils;
import com.urbaniza.authapi.service.AuthService;
import com.urbaniza.authapi.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDTO signupRequest) {
        try {
            authService.signup(signupRequest);
            return ResponseEntity.ok("Usuário registrado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao registrar: " + e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SigninRequestDTO signinRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String accessToken = jwtUtils.generateAccessToken(userDetails);

            String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            Date expiration = jwtUtils.getExpirationDateFromToken(accessToken);

            SigninResponseDTO response = new SigninResponseDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setExpTime(expiration.getTime());

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            if (e.getMessage().contains("Confirme seu email")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Verifique seu email e confirme sua conta");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @GetMapping("/confirm-email")
    public ResponseEntity<?> confirmEmail(@RequestParam String token) {
        try {
            User user = userRepository.findByConfirmationToken(token)
                    .orElseThrow(() -> new RuntimeException("Token inválido"));

            user.setEmailConfirmed(true);
            user.setConfirmationToken(null); // Invalida o token após uso
            userRepository.save(user);

            return ResponseEntity.ok("Email confirmado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao confirmar email: " + e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logout realizado com sucesso.");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtUtils.validateJwtToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token inválido ou expirado.");
        }

        String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }

        String newAccessToken = jwtUtils.generateAccessToken(user.get());
        long expTime = jwtUtils.getExpirationDateFromToken(newAccessToken).getTime();

        RefreshTokenResponseDTO response = new RefreshTokenResponseDTO();
        response.setAccessToken(newAccessToken);
        response.setExpTime(expTime);

        return ResponseEntity.ok(response);
    }

}
