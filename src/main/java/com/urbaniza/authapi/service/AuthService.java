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
    private Integer TOKEN_TTL = 25;

    public void signup(String email, String password) throws Exception {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Optional<User> userFound = UserRepository.findByEmail(email);
        if (userFound.isPresent()) {
            throw new Exception("Email already exist");
        }

        UserRepository.save(user);
    }

    public Token signin(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Optional<User> userFound = UserRepository.findByEmail(email);
        if (userFound.isPresent() && userFound.get().getPassword().equals(password)) {
            Token token = new Token();
            token.setUser(userFound.get());
            token.setToken(UUID.randomUUID().toString()); // TODO criar logica tkn
            token.setExpTime(Instant.now().plusSeconds(TOKEN_TTL).toEpochMilli());
            token = TokenRepository.save(token);
            return token;
        }
        return null;
    }

    public Boolean validade(String token) {

        Optional<Token> found = TokenRepository.findByToken(token);
        return found.isPresent() && found.get().getExpTime() >Instant.now().toEpochMilli();
    }

}
