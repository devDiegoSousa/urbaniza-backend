package com.urbaniza.authapi.service;

import java.util.Optional;
import java.util.UUID;

import com.urbaniza.authapi.dto.auth.signin.SigninReturnDTO;
import com.urbaniza.authapi.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.urbaniza.authapi.model.User;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EmailService emailService;


    public void signup(SignupRequestDTO signupRequestDTO) throws Exception {
        Optional<User> userFound = userRepository.findByEmail(signupRequestDTO.getEmail());
        if (userFound.isPresent()) {throw new Exception("Email already exists");}

        String emailToConfirm = signupRequestDTO.getEmail();
        String confirmationToken = jwtUtils.generateConfirmEmailToken(emailToConfirm);

        User user = new User();
        user.setConfirmationToken(confirmationToken);
        user.setEmail(signupRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        user.setFirstName(signupRequestDTO.getFirstName());
        user.setLastName(signupRequestDTO.getLastName());
        user.setRole(UserRole.CITIZEN);
        
        userRepository.save(user);

        emailService.sendConfirmationEmail(user.getEmail(), confirmationToken);
    }

    public SigninReturnDTO signin(SigninRequestDTO signinRequestDTO) {
        Optional<User> userFound = userRepository.findByEmail(signinRequestDTO.getEmail());
        if (userFound.isPresent() && passwordEncoder.matches(signinRequestDTO.getPassword(), userFound.get().getPassword())) {
            User user = userFound.get();
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.generateAccessToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(user);
            long expTime = jwtUtils.getExpirationDateFromToken(accessToken).getTime();

            return new SigninReturnDTO(accessToken, expTime, refreshToken);
        }
        return null;
    }

    public User getUserDetails(String email) {return userRepository.findByEmail(email).orElse(null);}
}

