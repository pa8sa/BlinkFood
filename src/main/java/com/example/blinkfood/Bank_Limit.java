package com.example.blinkfood;

public class Bank_Limit extends Bank{
    public Bank_Limit (double Cost, double Balance) {
        super(Cost, Balance, 0);
    }

    @Override
    public double Buy (double Balance, double Cost) {
        if (Balance - Cost >= 10000) {
            Balance -= Cost;
            return Balance;
        }
        else return -1;
    }
}
