package com.example.service.dto;

public class ConnectionDto {
    private String email;

    public ConnectionDto(String email) {
        this.email = email;
    }

    public ConnectionDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
