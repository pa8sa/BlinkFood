package com.example.blinkfood;

public class Bank_Normal extends Bank{
    public Bank_Normal (double Cost, double Balance) {
        super(Cost, Balance);
        super.Tax = 0;
    }

    @Override
    public double Buy (double Balance, double Cost) {
        if (Balance - Cost >= 0) {
            Balance -= Cost;
            return Balance;
        }
        else return -1;
    }
}
