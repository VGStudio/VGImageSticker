package com.app.vgs.vgimagesticker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActionActivity extends BaseActivity {

    public static final int STICKER_ACTIVITY_CODE = 1001;

    View mExitPopUp;
    private AdView mBannerAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);
        initView();
        initData();
    }

    private void initView() {
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);

        mExitPopUp.setVisibility(View.GONE);
    }

    private void initData() {
        initAds();

    }

    public void stickerClick(View view){
        openStickerActivity();
    }

    private void openStickerActivity(){
        Intent intent = new Intent(this, StickerActivity.class);
        startActivityForResult(intent, STICKER_ACTIVITY_CODE);
    }


    @Override
    public void setShowInterstitial() {
        mShowInterstitial = true;
    }

    private void initAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.loadAd(adRequest);
    }

    public void noExitClick(View view){
        hideExitPop();
    }
    public void yesExitClick(View view){
        if(!showInterstitial()){
            finish();
        }
    }

    public void backClick(View view){
        exitScreen(true);
    }
    private void showExitPop(){
        mExitPopUp.setVisibility(View.VISIBLE);
    }
    private void hideExitPop(){
        mExitPopUp.setVisibility(View.GONE);
    }

    public void exitScreen(boolean confirmExit){
        if(confirmExit){
            showExitPop();
        }else{
            finish();
        }
    }

    public void closeInterstitial() {
        finish();
    }

    @Override
    public void onBackPressed() {
        showExitPop();
    }
}
