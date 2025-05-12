package com.urbaniza.authapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens") // Alterado o nome da tabela para o plural
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use IDENTITY para PostgreSQL
    private Long id;

    @Column(name = "token", nullable = false, unique = true) // Adicionadas constraints
    private String token;

    @ManyToOne(fetch = FetchType.LAZY) // Alterado para LAZY loading
    @JoinColumn(name = "user_id", nullable = false) // Adicionada coluna para a chave estrangeira
    private User user;

    @Column(name = "exp_time", nullable = false)
    private Long expTime;

    public Token() {}

    public Token(Long id, String token, User user, Long expTime) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expTime = expTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getExpTime() {
        return expTime;
    }

    public void setExpTime(Long expTime) {
        this.expTime = expTime;
    }
}
