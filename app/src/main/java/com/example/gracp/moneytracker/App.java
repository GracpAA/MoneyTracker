package com.example.gracp.moneytracker;

import android.app.Application;

import com.example.gracp.moneytracker.api.Api;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;
import static okhttp3.logging.HttpLoggingInterceptor.Level.NONE;

/**
 * Created by gracp on 08.11.2017.
 */

public class App extends Application {

    private Api api;

    @Override
    public void onCreate() {
        super.onCreate();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? BODY : NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                                .addInterceptor(interceptor)
                                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://loftschoolandroid1117.getsandbox.com/").addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }
}
