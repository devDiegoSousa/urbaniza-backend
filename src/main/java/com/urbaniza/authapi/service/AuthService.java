package com.urbaniza.authapi.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.urbaniza.authapi.model.Token;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.TokenRepository;
import com.urbaniza.authapi.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private TokenRepository TokenRepository;
    @Autowired
    private EmailService emailService; // Adicione esta dependência!

    // Tempo de expiração do token de verificação (24 horas em milissegundos)
    private static final long EMAIL_VERIFICATION_EXPIRATION = 24 * 60 * 60 * 1000;

    // Tempo de expiração do token de autenticação (30 segundos)
    private static final Integer TOKEN_TTL = 30;

    public void signup(String email, String password) throws Exception {
        Optional<User> userFound = UserRepository.findByEmail(email);
        if (userFound.isPresent()) {
            throw new Exception("Email already exist");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setVerified(false);
        UserRepository.save(user);

        Token veToken = new Token(
                user,
                EMAIL_VERIFICATION_EXPIRATION
        );

        TokenRepository.save(veToken);

        emailService.sendVerificationEmail(email, veToken.getToken());
    }

    public boolean verifyEmail(String token) {
        Optional<Token> verificationToken = TokenRepository.findByToken(token);
        if (verificationToken.isPresent() &&
                verificationToken.get().getExpTime() > Instant.now().toEpochMilli()) {

            User user = verificationToken.get().getUser();
            user.setVerified(true);
            UserRepository.save(user);

            TokenRepository.delete(verificationToken.get()); // Remove o token após uso
            return true;
        }
        return false;
    }

    public Token signin(String email, String password) {
        Optional<User> userFound = UserRepository.findByEmail(email);
        if(userFound.isPresent() && userFound.get().getPassword().equals(password) && userFound.get().isVerified()) {
            Token authToken = new Token();
            authToken.setUser(userFound.get());
            authToken.setToken(UUID.randomUUID().toString());
            authToken.setExpTime(Instant.now().plusSeconds(TOKEN_TTL).toEpochMilli());
            return TokenRepository.save(authToken);
        }
        return null;
    }

    public Boolean validade(String token) {

        Optional<Token> found = TokenRepository.findByToken(token);
        return found.isPresent() && found.get().getExpTime() >Instant.now().toEpochMilli();
    }

}
