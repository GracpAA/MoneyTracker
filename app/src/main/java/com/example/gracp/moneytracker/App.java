package com.example.gracp.moneytracker;

import android.app.Application;
import android.text.TextUtils;

import com.example.gracp.moneytracker.api.Api;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                                .addInterceptor(new AuthInterceptor())
                                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://android.loftschool.com/basic/v1/").addConverterFactory(GsonConverterFactory.create(gson)).client(client).build();
        api = retrofit.create(Api.class);
    }

    public Api getApi() {
        return api;
    }


    public void setAuthToken(String authToken) {

        getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).edit().putString(KEY_AUTH_TOKEN, authToken).apply();
    }

    public String getAuthToken() {
        return getSharedPreferences(PREFERENCES_SESSION, MODE_PRIVATE).getString(KEY_AUTH_TOKEN, "");
    }

    public boolean isLoggedIn() {
        return !TextUtils.isEmpty(getAuthToken());
    }


    private class AuthInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            HttpUrl url = originalRequest.url().newBuilder().addQueryParameter("auth-token", getAuthToken()).build();
            return chain.proceed(originalRequest.newBuilder().url(url).build());
        }
    }

    private static final String PREFERENCES_SESSION = "session";
    private static final String KEY_AUTH_TOKEN = "auth-token";
}
