package com.timothypuglia.scumbagpug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;

/**
 * Created by Mischa de Haan on 13-4-2018.
 */

public class HelperSharedPreferences {

    public static class SharedPreferencesKeys{
        public static final String High_score ="High_score";
        public static final String High_score2 ="High_score2";
        public static final String High_score3 ="High_score3";
    }

    public static void putSharedPreferencesInt(Context context, String key, int value){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        Editor edit=preferences.edit();
        edit.putInt(key,value);
        edit.commit();
    }

    public static int getSharedPreferencesInt(Context context,String key, int _default){
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, _default);
    }
}
