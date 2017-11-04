package com.example.gracp.moneytracker;

/**
 * Created by gracp on 03.11.2017.
 */

public class Item {

    private String name;
    private int price;

    Item(String name, int price){
        this.name = name;
        this.price = price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
