package com.example.model;

import javax.persistence.*;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ")
    @SequenceGenerator(name = "ACCOUNT_SEQ", sequenceName = "ACCOUNT_SEQ", allocationSize = 1)
    private Integer id;

    private Double balance;

    private String iban;

    public Account(Integer id, Double balance, String iban) {
        this.id = id;
        this.balance = balance;
        this.iban = iban;
    }

    public Account() {
    }

    public Account plus(double balance) {
        this.balance += balance;
        return this;
    }

    public Account minus(double balance) {
        this.balance -= balance;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}



