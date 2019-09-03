package com.app.vgs.vgimagesticker.ads;

import android.app.Activity;

import com.app.vgs.vgimagesticker.BaseActivity;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdmobUtils {

    private BaseActivity mParent;


    private InterstitialAd mInterstitialAd;

    public AdmobUtils(BaseActivity mParent) {
        this.mParent = mParent;
        loadInterstitialAd();
    }

    public void loadInterstitialAd(){
        try {
            if(mInterstitialAd != null && mInterstitialAd.isLoading()){
                return;
            }
            mInterstitialAd = null;
            mInterstitialAd = new InterstitialAd(mParent);
            mInterstitialAd.setAdUnitId(AdUtils.getAdmobInterstitialId(mParent));
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            interstitialEvents();

        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }

    public boolean isInterstitialLoaded(){
        if(mInterstitialAd != null){
            return mInterstitialAd.isLoaded();
        }
        return false;
    }

    public boolean showInterstitial(){
//        if(isInterstitialLoaded()){
//            mInterstitialAd.show();
//            return true;
//        }
        return false;
    }

    public void interstitialEvents(){
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mParent.closeInterstitial();
            }
        });
    }
}
