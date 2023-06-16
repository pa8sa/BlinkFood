package com.example.blinkfood;

public abstract class Bank {
    protected double Tax;
    private double Cost;
    private double Balance;
    public Bank (double Cost, double Balance, double Tax) {
        this.Cost = Cost;
        this.Balance = Balance;
        this.Tax = Tax;
    }

    public double getBalance() {
        return Balance;
    }

    public double getCost() {
        return Cost;
    }

    public double getTax() {
        return Tax;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public abstract double Buy (double Balance, double Cost);
}
