package com.yeuyt.mygit.tools.utils;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yeuyt.mygit.BuildConfig;

public class LogUtils {
    private static final String Tag = "MyGit";

    public static void init(){
        FormatStrategy strategy = PrettyFormatStrategy.newBuilder()
                .tag(Tag).build();
        Logger.addLogAdapter(new AndroidLogAdapter(strategy));
    }
    public static void i(String msg) {
        if(BuildConfig.DEBUG) {
            Logger.i(msg);
        }
    }
    public static void d(String msg) {
        if(BuildConfig.DEBUG) {
            Logger.d(msg);
        }
    }
    public static void w(String msg) {
        if(BuildConfig.DEBUG) {
            Logger.w(msg);
        }
    }
    public static void e(String msg) {
        if(BuildConfig.DEBUG) {
            Logger.e(msg);
        }
    }
    public static void e(Throwable e) {
        if(BuildConfig.DEBUG) {
           Logger.e(e, "");
        }
    }
}
