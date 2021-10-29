package com.example.service.form;

import org.springframework.lang.Nullable;

public class SearchTransactionForm {
    @Nullable
    private double amountMin;
    @Nullable
    private double amountMax;
    @Nullable
    private String startDate;
    @Nullable
    private String endDate;
    @Nullable
    private String email;
    @Nullable
    private String description;

    public SearchTransactionForm() {
    }

    public SearchTransactionForm(@Nullable double amountMin, @Nullable double amountMax, @Nullable String startDate, @Nullable String endDate, @Nullable String email, @Nullable String description) {
        this.amountMin = amountMin;
        this.amountMax = amountMax;
        this.startDate = startDate;
        this.endDate = endDate;
        this.email = email;
        this.description = description;
    }

    public double getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(double amountMin) {
        this.amountMin = amountMin;
    }

    public double getAmountMax() {
        return amountMax;
    }

    public void setAmountMax(double amountMax) {
        this.amountMax = amountMax;
    }

    @Nullable
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(@Nullable String startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable String endDate) {
        this.endDate = endDate;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}
