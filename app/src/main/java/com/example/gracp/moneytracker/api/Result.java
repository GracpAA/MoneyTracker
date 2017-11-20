package com.example.gracp.moneytracker.api;

import android.text.TextUtils;

/**
 * Created by gracp on 19.11.2017.
 */

public class Result {
    String status;
    public boolean isSuccess() {
        return TextUtils.equals(status, "success");
    }
}
