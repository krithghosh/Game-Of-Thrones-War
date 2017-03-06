package com.got.krith.gameofthrones.application;

import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by krith on 05/03/17.
 */

public class SharedPreferenceManager {

    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor editor;

    public static final String IS_CONTACTS_FETCHED_FROM_SERVER = "is_contact_fetched_from_server";

    public SharedPreferenceManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        editor = sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value).commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value).commit();
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value).commit();
    }

    public float getFloat(String key) {
        return sharedPreferences.getLong(key, 0);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putStringSet(String key, Set<String> value) {
        editor.putStringSet(key, value).commit();
    }

    public Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    public void removeValue(String key) {
        editor.remove(key).commit();
    }

    public void removeAll() {
        editor.clear().commit();
    }
}
