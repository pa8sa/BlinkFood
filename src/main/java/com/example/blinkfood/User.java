package com.example.blinkfood;

import javafx.util.converter.ShortStringConverter;

public class User {
    private String UserName;
    private String PhoneNumber;
    private String Email;
    private String PassWord;
    private String Address;
    private double Balance;
    public User (String UserName, String PassWord, String PhoneNumber, String Address, String Email) {
        this.UserName = UserName;
        this.PhoneNumber = PhoneNumber;
        this.Email = Email;
        this.PassWord = PassWord;
        this.Address = Address;
        this.Balance = 0;
    }
    public String getUserName() {
        return UserName;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public String getEmail() {
        return Email;
    }
    public String getPassWord() {
        return PassWord;
    }
    public String getAddress() {
        return Address;
    }
    public double getBalance() {
        return Balance;
    }
}
