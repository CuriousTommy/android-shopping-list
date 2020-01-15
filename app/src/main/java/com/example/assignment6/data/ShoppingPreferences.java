package com.example.assignment6.data;

import android.content.SharedPreferences;

public class ShoppingPreferences {
    public static String name = "ShoppingPreferences";
    public static String parent_id = "PARENT_ID";

    public static long getParentID(SharedPreferences mSharedPreferences) {
        return mSharedPreferences.getLong(ShoppingPreferences.parent_id,-1);
    }
}
