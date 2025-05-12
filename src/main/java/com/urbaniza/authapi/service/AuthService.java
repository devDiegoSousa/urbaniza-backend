package com.urbaniza.authapi.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.TokenRepository;
import com.urbaniza.authapi.repository.UserRepository;
import com.urbaniza.authapi.dto.auth.SignupRequestDTO;
import com.urbaniza.authapi.dto.auth.SigninRequestDTO;
import com.urbaniza.authapi.security.JwtUtils;
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


    public void signup(SignupRequestDTO signupRequestDTO) throws Exception {
        User user = new User();
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        user.setFirstName(signupRequestDTO.getFirstName());
        user.setLastName(signupRequestDTO.getLastName());

        Optional<User> userFound = userRepository.findByEmail(signupRequestDTO.getEmail());

        if (userFound.isPresent()) {throw new Exception("Email já existe");}

        userRepository.save(user);
    }

    public String signin(SigninRequestDTO signinRequestDTO) {
        Optional<User> userFound = userRepository.findByEmail(signinRequestDTO.getEmail());
        if (userFound.isPresent() && passwordEncoder.matches(signinRequestDTO.getPassword(), userFound.get().getPassword())) {
            // Criando o token de autenticação com email e senha (a senha será 'null' após a verificação)
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    signinRequestDTO.getEmail(), signinRequestDTO.getPassword(), userFound.get().getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtUtils.generateJwtToken(authentication);
        }
        return null;
    }

    public User getUserDetails(String email) {return userRepository.findByEmail(email).orElse(null);}
}
