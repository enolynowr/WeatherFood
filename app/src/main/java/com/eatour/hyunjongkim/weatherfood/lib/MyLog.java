package com.eatour.hyunjongkim.weatherfood.lib;

import android.util.Log;

import com.eatour.hyunjongkim.weatherfood.BuildConfig;

// BuildConfig.DEBUGの値を利用、開発の時はログ出力、リリースの時はログ未出力
public class MyLog {
    private static boolean enabled = BuildConfig.DEBUG;

    public static void d(String tag, String text) {
        if (!enabled) return;
        Log.d(tag, text);
    }

    public static void d(String text) {
        if (!enabled) return;
        Log.d("tag", text);
    }

    public static void d(String tag, Class<?> cls, String text) {
        if (!enabled) return;
        Log.d(tag, cls.getName() + "." + text);
    }

    public static void e(String tag, String text) {
        if (!enabled) return;
        Log.e(tag, text);
    }
}
