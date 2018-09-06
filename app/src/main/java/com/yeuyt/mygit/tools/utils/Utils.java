package com.yeuyt.mygit.tools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;

import com.yeuyt.mygit.di.GitApplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static  Toast t;

    public static String getString(int id) {
        return GitApplication.getContext().getString(id);
    }

    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert manager != null;
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    public static void showToastLong(String msg) {
        if (t == null)
            t = Toast.makeText(GitApplication.getContext(), null, Toast.LENGTH_LONG);
        t.setText(msg);
        t.setDuration(Toast.LENGTH_LONG);
        t.show();
    }

    public static void showToastShort(String msg) {
        if (t == null)
            t = Toast.makeText(GitApplication.getContext(), null, Toast.LENGTH_LONG);
        t.setText(msg);
        t.setDuration(Toast.LENGTH_SHORT);
        t.show();
    }

    public static String append(CharSequence... arr) {
        StringBuilder builder = new StringBuilder();
        for (CharSequence s : arr) {
            builder.append(s);
        }
        return builder.toString();
    }

    public static String[] parseGithubUrl(String rawUrl) {
        Matcher m = Pattern
//                .compile("https://github\\.com/(.*?)/(((.*?)/.*)|(.*))")
//                .compile("https://github\\.com/(.*?)/(?:(?:(.*?)/.*)|(.*))")
                .compile("https://github\\.com/([^/]*)/([^/]*)")
                .matcher(rawUrl);
        String[] result = new String[2];
        if (m.find()) {
            try {
                if (!TextUtils.isEmpty(m.group(1)))
                    result[0] = m.group(1);
                else return null;
                if (!TextUtils.isEmpty(m.group(2)))
                    result[1] = m.group(2);
                else return null;
            } catch (Exception e) {
                return null;
            }
        } else return null;
        return result;
    }
}
