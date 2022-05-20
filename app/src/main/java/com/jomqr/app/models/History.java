package com.jomqr.app.models;

import java.io.Serializable;

public class History implements Serializable {

    private String amount, balance;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
