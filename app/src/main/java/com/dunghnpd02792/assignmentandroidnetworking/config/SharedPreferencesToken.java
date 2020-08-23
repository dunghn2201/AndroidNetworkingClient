package com.dunghnpd02792.assignmentandroidnetworking.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created on 7/30/2020
 */
public class SharedPreferencesToken {
    Context context;
    SharedPreferences sharedPreferences;
    private static final String NAME_LOGIN = "isLogin";
    private static final String USERNAME = "email";
    private static final String HASHED_PASSWORD = "token";
    private static final String SAVE = "isSave";

    public SharedPreferencesToken(Context context) {
        this.context = context;
    }

    //lưu mật khẩu
    public void SavePass(String email, String token, boolean save) {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, email);
        editor.putString(HASHED_PASSWORD, token);
        editor.putBoolean(SAVE, save);
        editor.apply();
    }

    public String isCheckLogin() {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, 0);
        return sharedPreferences.getString(USERNAME, "");
    }

    public Boolean isCheckSaveLogin() {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, 0);
        return sharedPreferences.getBoolean(SAVE, true);
    }


    //thoát
    public boolean loginOut() {
        sharedPreferences = context.getSharedPreferences(NAME_LOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(SAVE, false);
        editor.apply();
        return sharedPreferences.getBoolean(SAVE, false);
    }
}