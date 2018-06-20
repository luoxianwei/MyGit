package com.yeuyt.mygit.tools.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yeuyt.mygit.model.entity.UserInfo;

public class AccountHelper {
    private static UserInfo userInfo;
    private static final String LOGIN_TOKEN = "login_token";
    private static final String LOGIN_USERINFO = "login_userinfo";
    private static final String LOGIN_USER_NAME = "login_user_name";

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("AccountHelper", Context.MODE_PRIVATE);
    }

    public static void saveLoginToken(Context context, String token) {
        getPreferences(context).edit().putString(LOGIN_TOKEN, token).apply();
    }
    public static String getLoginToken(Context context) {
        return getPreferences(context).getString(LOGIN_TOKEN, null);
    }

    public static void saveLoginUser(Context context, UserInfo info) {
        String userJson = new Gson().toJson(info);
        getPreferences(context).edit()
                .putString(LOGIN_USERINFO, userJson)
                .putString(LOGIN_USER_NAME, info.login)
                .apply();
    }

    public static UserInfo getUserInfo(Context context) {
        if (userInfo != null) {
            return userInfo;
        }
        String info = getPreferences(context).getString(LOGIN_USERINFO, null);
        if (!TextUtils.isEmpty(info)) {
            userInfo = new Gson().fromJson(info, UserInfo.class);
        }
        return userInfo;
    }

    public static String getLoginUser(Context context) {
        return getPreferences(context).getString(LOGIN_USER_NAME, null);
    }

    public static void removeUserInfo(Context context) {
        userInfo = null;
        getPreferences(context).edit()
                .remove(LOGIN_USERINFO)
                .remove(LOGIN_USER_NAME)
                .apply();
    }
    public static boolean isLogin(Context context) {
        System.out.println(!TextUtils.isEmpty(getLoginToken(context)) && getUserInfo(context) != null);
        return !TextUtils.isEmpty(getLoginToken(context)) && getUserInfo(context) != null;
    }
}
