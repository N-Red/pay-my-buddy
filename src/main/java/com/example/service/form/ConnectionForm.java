package com.example.service.form;

public class ConnectionForm {
    private String email;

    public ConnectionForm(String email) {
        this.email = email;
    }

    public ConnectionForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
