package com.example.blinkfood;

public class Food {
    private String Name;
    private double Price;
    private double Weight;
    private enum Food_Type {Sonati, FastFood};
    private Food_Type Type;
    private int Count;
    private String IMGpath;


    public Food (String Name, double Price, double Weight, String Type, String imgpath) {
        this.Name = Name;
        this.Weight = Weight;
        this.Price = Price;
        IMGpath = imgpath;
        if (Type.equals("Sonati")) {
            this.Type = Food_Type.Sonati;
        }
        else this.Type = Food_Type.FastFood;
    }

    public String getName() {
        return Name;
    }

    public double getPrice() {
        return Price;
    }

    public double getWeight() {
        return Weight;
    }

    public Food_Type getType() {
        return Type;
    }

    public void setType(Food_Type type) {
        Type = type;
    }

    public void setIMGpath(String IMGpath) {
        this.IMGpath = IMGpath;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCount(int count) {
        Count = count;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public void setWeight(double weight) {
        Weight = weight;
    }

    public int getCount() {
        return Count;
    }

    public String getIMGpath() {
        return IMGpath;
    }

    public void addCount() {
        Count++;
    }

    public void reduceCount() {
        Count--;
    }
}
