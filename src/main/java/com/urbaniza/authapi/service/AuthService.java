package com.urbaniza.authapi.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.urbaniza.authapi.model.User;
import com.urbaniza.authapi.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registra um novo usuário no sistema.
    public void signup(String email, String password) throws Exception {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Criptografa a senha
        Optional<User> userFound = userRepository.findByEmail(email);
        if (userFound.isPresent()) {
            throw new Exception("Email já existe");
        }
        userRepository.save(user);
    }

    //Este método agora apenas recupera o usuário do banco de dados
    public User signin(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            return null; // Retorna null se a autenticação falhar
        }
        return user.orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado com email: " + email);
        }
        return UserDetailsImpl.build(user.orElse(null)); // Converte User para UserDetails
    }
}
//implementação de UserDetails
class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    //private Collection<? extends GrantedAuthority> authorities; // Não estamos usando authorities neste exemplo, mas pode ser necessário no seu caso

    public UserDetailsImpl(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getEmail(),
                user.getPassword());
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public String getPassword() {return password;}
    @Override
    public boolean isAccountNonExpired() {return true;}
    @Override
    public boolean isAccountNonLocked() {return true;}
    @Override
    public boolean isCredentialsNonExpired() {return true;}
    @Override
    public boolean isEnabled() {return true;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }
}
