package com.example.blinkfood;

import java.util.ArrayList;

public class Restaurant {
    private String Name;
    private String Address;
    private String WorkTime;
    private ArrayList<Food> Foods = new ArrayList<>();

    public enum Res_Type {BiroonBar, BaMiz};
    private Res_Type Type;
    private int FoodsCount;
    private int Chair_Delivery_Count;

    public Restaurant(String name, String address, String workTime, String type, int foodsCount, int Miz_Peyk) {
        Name = name;
        Address = address;
        WorkTime = workTime;
        FoodsCount = foodsCount;
        Chair_Delivery_Count = Miz_Peyk;
        if (type.equals("BiroonBar")) {
            Type = Res_Type.BiroonBar;
        }
        else Type = Res_Type.BaMiz;
    }

    public int getChair_Delivery_Count() {
        return Chair_Delivery_Count;
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

    public String getWorkTime() {
        return WorkTime;
    }

    public ArrayList<Food> getFoods() {
        return Foods;
    }

    public int getFoodsCount() {
        return FoodsCount;
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

    public void setFoodsCount(int foodsCount) {
        FoodsCount = foodsCount;
    }

    public void setChair_Delivery_Count(int Chair_Delivery) {
        Chair_Delivery_Count = Chair_Delivery;
    }

    public void addFood (Food food) {
        Foods.add(Foods.size(), food);
    }
}
