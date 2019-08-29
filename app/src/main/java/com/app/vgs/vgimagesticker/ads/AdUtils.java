package com.app.vgs.vgimagesticker.ads;

import android.content.Context;

import com.app.vgs.vgimagesticker.R;

public class AdUtils {

    public static String getAdmobNativeId(Context context){
        String str = context.getString(R.string.admob_native_ads);
        str = "ca-app-pub-3940256099942544/2247696110";
        return str;
    }

    public static String getAdmobInterstitialId(Context context){
        String str = context.getString(R.string.admob_interstitial_ads);
        str = "ca-app-pub-3940256099942544/1033173712";
        return str;
    }

    public static String getAdmobBannerId(Context context){
        String str = context.getString(R.string.admob_banner_ads);
        str = "ca-app-pub-3940256099942544/6300978111";
        return str;
    }
}
