package com.example.blinkfood;

import java.util.ArrayList;

public class Restaurant {
    private String Name;
    private String WorkTime;
    private ArrayList<String> Foods = new ArrayList<>();
    private enum Res_Type {BiroonBar, BaMiz};
    private Res_Type Type;
}
