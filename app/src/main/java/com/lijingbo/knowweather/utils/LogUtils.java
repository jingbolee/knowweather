package com.lijingbo.knowweather.utils;

import android.util.Log;

/**
 * @FileName: com.lijingbo.knowweather.utils.LogUtils.java
 * @Author: Li Jingbo
 * @Date: 2016-03-30 21:29
 * @Version V1.0 Log工具类
 */
public class LogUtils {
    private static final String TAG = "LogUtils";

    public static int level = 0;
    private static final int VERBOSE = 5;
    private static final int DEBUG = 4;
    private static final int INFO = 3;
    private static final int WRAN = 2;
    private static final int ERROR = 1;

    public static void v(String tag, String msg) {
        if ( level <= VERBOSE ) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if ( level <= DEBUG ) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if ( level <= INFO ) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if ( level <= WRAN ) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if ( level <= ERROR ) {
            Log.e(tag, msg);
        }
    }
}
