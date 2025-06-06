package com.urbaniza.authapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {
    ADMIN("ADMIN"),
    DEPARTMENT("DEPARTMENT"),
    CITIZEN("CITIZEN");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getRole() {
        return role;
    }

    @JsonCreator
    public static UserRole fromRole(String value) {
        for (UserRole userRole : values()) {
            if (userRole.getRole().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }
}
