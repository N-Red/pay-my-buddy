package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSFER_SEQ")
    @SequenceGenerator(name = "TRANSFER_SEQ", sequenceName = "TRANSFER_SEQ", allocationSize = 1)
    private Integer id;
    private LocalDateTime date;
    @ManyToOne
    private User from;
    @ManyToOne
    private User to;
    private Double amountBeforeFee;
    private Double amountAfterFee;
    private String description;

    public Transaction(Integer id, LocalDateTime date, User from, User to, Double amountBeforeFee, Double amountAfterFee, String description) {
        this.id = id;
        this.date = date;
        this.from = from;
        this.to = to;
        this.amountBeforeFee = amountBeforeFee;
        this.amountAfterFee = amountAfterFee;
        this.description = description;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public Double getAmountBeforeFee() {
        return amountBeforeFee;
    }

    public void setAmountBeforeFee(Double amountBeforeFee) {
        this.amountBeforeFee = amountBeforeFee;
    }

    public Double getAmountAfterFee() {
        return amountAfterFee;
    }

    public void setAmountAfterFee(Double amountAfterFee) {
        this.amountAfterFee = amountAfterFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


