package com.example.service.dto;

public class TransactionForm {
    private String email;
    private Double amount;
    private String description;

    public TransactionForm(String email, Double amount, String description) {
        this.email = email;
        this.amount = amount;
        this.description = description;
    }

    public TransactionForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
