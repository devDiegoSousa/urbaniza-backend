package com.urbaniza.authapi.service;

import java.util.Optional;
import java.util.UUID;

import com.urbaniza.authapi.dto.auth.signin.SigninResponseDTO;
import com.urbaniza.authapi.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.TokenRepository;
import com.urbaniza.authapi.repository.UserRepository;
import com.urbaniza.authapi.dto.auth.signup.SignupRequestDTO;
import com.urbaniza.authapi.dto.auth.signin.SigninRequestDTO;
import com.urbaniza.authapi.util.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;


    public void signup(SignupRequestDTO signupRequestDTO) throws Exception {
        Optional<User> userFound = userRepository.findByEmail(signupRequestDTO.getEmail());
        if (userFound.isPresent()) {throw new Exception("Email já existe");}

        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        user.setFirstName(signupRequestDTO.getFirstName());
        user.setLastName(signupRequestDTO.getLastName());

        user.setRole(UserRole.CITIZEN);

        // Gera o token e atualiza o usuário
        String confirmationToken = UUID.randomUUID().toString();
        user.setConfirmationToken(confirmationToken);
        userRepository.save(user); // Salva no banco

        // Envia o email (chama o EmailService)
        emailService.sendConfirmationEmail(user.getEmail(), confirmationToken);
    }

    public SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) {
        Optional<User> userFound = userRepository.findByEmail(signinRequestDTO.getEmail());
        if (userFound.isPresent() && passwordEncoder.matches(signinRequestDTO.getPassword(), userFound.get().getPassword())) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    signinRequestDTO.getEmail(), null, userFound.get().getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String accessToken = jwtUtils.generateAccessToken(userDetails);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            long expTime = jwtUtils.getExpirationDateFromToken(accessToken).getTime();

            SigninResponseDTO response = new SigninResponseDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setExpTime(expTime);

            return response;
        }
        return null;
    }

    public User getUserDetails(String email) {return userRepository.findByEmail(email).orElse(null);}
}
