package com.crupee.lottery.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.crupee.lottery.dashboard.model.LotteryDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefUtils {

    public static final String SHARED_PREF_KHUSHILOTTERY = "khushi_lottery";
    public static final String USER_DETAIL = "user_detail";
    public static final String IS_LOGIN = "is_login";

    public static String SAVED_LOTTERY_LIST = "saved_lottery_list";
    public static String SELECTED_LOTTERY_NUMBER = "selected_lottery_number";


    public static final String WALLET_PASSWORD = "wallet_password";

    public static void saveUserDetail(Context context, String userdetail) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_DETAIL, userdetail);
        editor.commit();
    }


    public static String returnUserDetail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        return pref.getString(USER_DETAIL, "");
    }

    public static void saveloggedIn(Context context, boolean loginIn) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LOGIN, loginIn);
        editor.commit();
    }


    public static Boolean returnLoggedIn(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        return pref.getBoolean(IS_LOGIN, false);
    }


    public static void saveLotteryList(Context context, ArrayList<LotteryDTO> list, String key) {
        removeList(context, key);
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(SAVED_LOTTERY_LIST, json);
        editor.apply();


    }

    public static ArrayList<LotteryDTO> returnLotteryList(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(SAVED_LOTTERY_LIST, null);
        Type type = new TypeToken<ArrayList<LotteryDTO>>() {
        }.getType();
        return gson.fromJson(json, type);

    }

    public static void clearLotteryList(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(SAVED_LOTTERY_LIST);
        editor.commit();

    }

    public static void removeList(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor spreferencesEditor = prefs.edit();
        spreferencesEditor.remove(SAVED_LOTTERY_LIST); //we are removing list by key
        spreferencesEditor.commit();
    }


    public static void saveSelectedLotteryNumber(Context context, ArrayList<Integer> list, String key) {
        removeList(context, key);
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(SELECTED_LOTTERY_NUMBER, json);
        editor.apply();


    }

    public static ArrayList<Integer> returnSelectedLotteryNumber(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(SELECTED_LOTTERY_NUMBER, null);
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return gson.fromJson(json, type);

    }
}
