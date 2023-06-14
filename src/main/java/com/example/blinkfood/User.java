package com.example.blinkfood;

import javafx.util.converter.ShortStringConverter;

public class User {
    private String UserName;
    private String PhoneNumber;
    private String Email;
    private String PassWord;
    private String Address;
    private double Balance = 0;

    public User () {

    }
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

    public void setAddress(String address) {
        Address = address;
    }

    public void setBalance(double balance) {
        Balance = balance;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
