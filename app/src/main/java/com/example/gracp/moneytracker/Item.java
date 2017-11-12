package com.example.gracp.moneytracker;

import java.io.Serializable;

/**
 * Created by gracp on 03.11.2017.
 */

public class Item implements Serializable {
    public static final String TYPE_UNKNOWN = "unknown";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";

    public int id;
    public String name;
    public int price;
    public String type;

    Item(String name, int price, String type){
        this.name = name;
        this.price = price;
        this.type = type;
    }
}
