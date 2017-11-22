package com.example.gracp.moneytracker.api;

import android.text.TextUtils;

import retrofit2.http.GET;

import com.example.gracp.moneytracker.AuthResult;
import com.example.gracp.moneytracker.Item;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gracp on 08.11.2017.
 */

public interface Api {

    @GET("balance")
    Call<BalanceResult> balance();

    @GET("items")
    Call<List<Item>> items(@Query("type") String type);

    @POST("items/add")
    Call<AddResult> add(@Query("name") String name, @Query("price") int price, @Query("type") String type);

    @GET("auth")
    Call<AuthResult> auth(@Query("social_user_id") String socialUserId);

    @POST("items/remove")
    Call<Result> remove(@Query("id") int id);

}
