package com.urbaniza.authapi.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.urbaniza.authapi.model.Token;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.TokenRepository;
import com.urbaniza.authapi.repository.UserRepository;

@Service
public class AuthService {
    @Autowired
    private UserRepository ur;
    @Autowired
    private TokenRepository tknRepository;
    private Integer TOKEN_TTL = 25;

    public void signup(String email, String password) throws Exception {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Optional<User> userFound = ur.findByEmail(email);
        if (userFound.isPresent()) {
            throw new Exception("Email already exist");
        }

        ur.save(user);
    }

    public Token signin(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        Optional<User> userFound = ur.findByEmail(email);
        if (userFound.isPresent() && userFound.get().getPassword().equals(password)) {
            Token tkn = new Token();
            tkn.setUser(userFound.get());
            tkn.setToken(UUID.randomUUID().toString()); // TODO criar logica tkn
            tkn.setExpTime(Instant.now().plusSeconds(TOKEN_TTL).toEpochMilli());
            tkn = tknRepository.save(tkn);
            return tkn;
        }
        return null;
    }

    public Boolean validade(String tkn) {
        // Resgata do banco de dados o tkn Bytoken
        // se existir no banco & a expiração é futura
        //então é validade


        Optional<Token> found = tknRepository.findByToken(tkn);
        return found.isPresent() && found.get().getExpTime() >Instant.now().toEpochMilli();
    }

}
