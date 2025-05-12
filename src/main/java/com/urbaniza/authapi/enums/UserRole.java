package com.urbaniza.authapi.enums;

public enum UserRole {
    DEPARTMENT("department"),

    CITIZEN("citizen");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
