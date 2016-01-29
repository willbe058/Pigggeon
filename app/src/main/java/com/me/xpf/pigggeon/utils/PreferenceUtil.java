package com.me.xpf.pigggeon.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.xpf.me.architect.app.AppData;

/**
 * Created by pengfeixie on 16/1/8.
 */
@SuppressWarnings("UnusedDeclaration")
public class PreferenceUtil {
    public static String getPreString(final String key, final String defaultValue) {
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(AppData.getContext());
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void setPreString(final String key, final String value) {
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(AppData.getContext());
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static boolean getPreBoolean(final String key, final boolean defaultValue) {
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(AppData.getContext());
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public static void setPreBoolean(final String key, final boolean value) {
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(AppData.getContext());
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void clearPreference(final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }
}
