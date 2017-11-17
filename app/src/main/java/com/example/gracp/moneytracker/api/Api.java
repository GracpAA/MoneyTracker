package com.example.gracp.moneytracker.api;

import retrofit2.http.GET;

import com.example.gracp.moneytracker.Item;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gracp on 08.11.2017.
 */

public interface Api {

    @GET("items")
    Call<List<Item>> items(@Query("type") String type);

    //@POST("items/add")
    //Call<Item> add(@Query("name") String name, @Query("price") int price, @Query("type") String type);

}
