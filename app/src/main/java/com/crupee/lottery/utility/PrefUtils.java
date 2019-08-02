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


    public static final String IS_LOGIN = "is_login";

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_TOKEN = "user_token";

    public static final String IS_LANGUAGE_SELECTED = "is_language_selected";
    public static final String SELECTED_LANGUAGE = "selected_language";
    public static final String LANGUAGE_REF = "language_ref";

    public static String SAVED_LOTTERY_LIST = "saved_lottery_list";
    public static String SELECTED_LOTTERY_NUMBER = "selected_lottery_number";


    public static final String WALLET_PASSWORD = "wallet_password";

    /*--- ---- ---- Login --- ---- ----*/

    public static void saveUserDetail(Context context, String id, String name, String email, String token) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_ID, id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }


    public static String returnUserName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        return pref.getString(USER_NAME, "");
    }

    public static String returnUserEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        return pref.getString(USER_EMAIL, "");
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

    /* ----- ---- ---- languages -- ---- ----- */

    public static void saveLanguage(Context context, Boolean language, String selectedlanguage, String language_ref) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_LANGUAGE_SELECTED, language);
        editor.putString(SELECTED_LANGUAGE, selectedlanguage);
        editor.putString(LANGUAGE_REF, language_ref);
        editor.commit();
    }


    public static Boolean isLanguageSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_LANGUAGE_SELECTED, false);
    }

    public static String returnlanguageSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        return prefs.getString(SELECTED_LANGUAGE, "en");
    }


    /*--- ---- --- save lottery list --- ---- -----*/

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


    public static void Logout(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_KHUSHILOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
