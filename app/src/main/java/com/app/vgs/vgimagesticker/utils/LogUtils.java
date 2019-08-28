package com.app.vgs.vgimagesticker.utils;

import android.util.Log;

public class LogUtils {
    public static final String TAG = "VGSTICKER";
    public static void d(String msg){
        Log.d(TAG, msg);
    }
    public static void e(Exception exp){
        Log.e(TAG, exp.getMessage(), exp);
    }
}
