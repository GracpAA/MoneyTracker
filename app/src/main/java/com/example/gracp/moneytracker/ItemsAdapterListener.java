package com.example.gracp.moneytracker;

/**
 * Created by gracp on 14.11.2017.
 */

public interface ItemsAdapterListener {
    void onItemClick(Item item, int position);
    void onItemLongClick(Item item, int position);
}
