package com.example.blinkfood;

import java.util.ArrayList;

public class Restaurant {
    private String Name;
    private String Address;
    private String WorkTime;
    private ArrayList<Food> Foods = new ArrayList<>();
    public enum Res_Type {BiroonBar, BaMiz};
    private Res_Type Type;
    private int Chair_Delivery_Count;
    private Boolean Enable;
    private String IMGpath;
    private int Res_ID;

    public Restaurant(String name, String address, String workTime, String type, int Miz_Peyk, Boolean enable, String imgpath, int res_id) {
        Name = name;
        Address = address;
        WorkTime = workTime;
        Chair_Delivery_Count = Miz_Peyk;
        Enable = enable;
        IMGpath = imgpath;
        Res_ID = res_id;
        if (type.equals("BiroonBar")) {
            Type = Res_Type.BiroonBar;
        }
        else Type = Res_Type.BaMiz;
    }

    public int getChair_Delivery_Count() {
        return Chair_Delivery_Count;
    }

    public Boolean getEnable() {
        return Enable;
    }

    public int getRes_ID() {
        return Res_ID;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public Res_Type getType() {
        return Type;
    }

    public String getIMGpath() {
        return IMGpath;
    }

    public String getWorkTime() {
        return WorkTime;
    }

    public ArrayList<Food> getFoods() {
        return Foods;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setType(Res_Type type) {
        Type = type;
    }

    public void setFoods(ArrayList<Food> foods) {
        Foods = foods;
    }

    public void setWorkTime(String workTime) {
        WorkTime = workTime;
    }

    public void setIMGpath(String IMGpath) {
        this.IMGpath = IMGpath;
    }

    public void setChair_Delivery_Count(int Chair_Delivery) {
        Chair_Delivery_Count = Chair_Delivery;
    }

    public void setEnable(Boolean enable) {
        Enable = enable;
    }

    public void setRes_ID(int res_ID) {
        Res_ID = res_ID;
    }

    public void addFood (Food food) {
        Foods.add(Foods.size(), food);
    }
}
