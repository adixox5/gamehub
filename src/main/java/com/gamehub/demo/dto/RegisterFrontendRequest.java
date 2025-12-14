package com.gamehub.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterFrontendRequest {

    @NotBlank
    @Size(min = 1, max = 120)
    private String username; // display name из фронта

    @NotBlank
    @Email
    @Size(max = 120)
    private String email;    // email из фронта (будет username в БД)

    @NotBlank
    @Size(min = 6, max = 200)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
