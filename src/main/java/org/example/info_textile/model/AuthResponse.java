package org.example.info_textile.model;

import lombok.Builder;
import lombok.Data;
import org.example.info_textile.model.entity.Role;


public class AuthResponse {
    private String token;
    private String username;

    // Konstruktor private bo'lishi kerak
    private AuthResponse(Builder builder) {
        this.token = builder.token;
        this.username = builder.username;
    }

    // Getterlar
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    // 🔨 Builder ichki klassi
    public static class Builder {
        private String token;
        private String username;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(this);
        }

        public Builder role(Role role) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "AuthResponse{token='" + token + "', username='" + username + "'}";
    }
}
