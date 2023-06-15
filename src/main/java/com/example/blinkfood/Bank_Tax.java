package com.example.blinkfood;

public class Bank_Tax extends Bank{
    public Bank_Tax (double Cost, double Balance) {
        super(Cost, Balance);
        super.Tax = 0.05;
    }

    public double Buy (double Balance, double Cost) {
        if (Balance - Cost - Tax * Cost >= 0) {
            Balance = Balance - (Cost + Tax * Cost);
            return Balance;
        }
        else return -1;
    }
}
