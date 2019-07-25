package com.crupee.lottery.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {

    public static final String SHARED_PREF_LOTTERY = "lottery";
    public static final String USER_DETAIL = "user_detail";
    public static final String IS_LOGIN = "is_login";


    public static final String WALLET_PASSWORD = "wallet_password";

    public static void saveUserDetail(Context context, String userdetail) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_LOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_DETAIL, userdetail);
        editor.commit();
    }


    public static String returnUserDetail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_LOTTERY, Context.MODE_PRIVATE);
        return pref.getString(USER_DETAIL, "{\n" +
                "   \"remark\":\"wallet\",\n" +
                "   \"user_id\":\"1\",\n" +
                "   \"user_name\":\" Rajan Shrestha\",\n" +
                "   \"mobile_number\":\"0123456789\"\n" +
                "}");
    }

    public static void saveloggedIn(Context context, String loginIn) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_LOTTERY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(IS_REGISTER, loginIn);
        editor.commit();
    }


    public static String returnLoggedIn(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_PREF_LOTTERY, Context.MODE_PRIVATE);
        return pref.getString(IS_REGISTER, "false");
    }
}
