package com.urbaniza.authapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String token;
    @ManyToOne @JsonIgnore
    private User user;
    private Long expTime;

    public Token() {

    }

    public Token(Integer id, String token, User user, Long expTime) {
        super();
        this.id = id;
        this.token = token;
        this.user = user;
        this.expTime = expTime;
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