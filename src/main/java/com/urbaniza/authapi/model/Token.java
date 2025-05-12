package com.urbaniza.authapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token; // armazena o UUID

    @ManyToOne @JsonIgnore
    private User user;

    private Long expTime;


    public Token() {

    }

    // Novo construtor simplificado para gerar UUID e expiração
    public Token(User user, long expirationTimeInMillis) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expTime = System.currentTimeMillis() + expirationTimeInMillis;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
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