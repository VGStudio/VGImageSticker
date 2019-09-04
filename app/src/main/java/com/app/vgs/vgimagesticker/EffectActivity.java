package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class EffectActivity extends BaseActivity {
    public static String KEY_GROUP_STICKER_ID = "KEY_GROUP_STICKER_ID";

    private String mFileSavedpath = null;


    RelativeLayout mRlHeader;
    LinearLayout mLlEditSticker;
    ImageView mIvPreview;
    View mExitPopUp;
    AdView mBannerAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        initView();
        initData();

    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = true;
    }

    @Override
    public void closeInterstitial() {
        goBackMainActionCategory();
    }

    private void initData() {
        initAds();
    }

    private void initAds(){
        if(NetworkUtils.isInternetConnected(this)){
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void initView() {

        mRlHeader = findViewById(R.id.rlHeader);
        mLlEditSticker = findViewById(R.id.llEditSticker);
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIvPreview = findViewById(R.id.ivPreview);






    }



    private void goBackMainActionCategory(){
        Intent intent = new Intent();
        if(mFileSavedpath != null){
            intent.putExtra(MainActionActivity.KEY_IMAGE_PATH_UPDATE, mFileSavedpath);
            setResult(Activity.RESULT_OK, intent);
        }else{
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
    }















    private void showExitPopUp(){
        mExitPopUp.setVisibility(View.VISIBLE);
    }

    private void hideExitPopUp(){
        mExitPopUp.setVisibility(View.GONE);
    }





    public void saveImageClick(View view){
        SaveFileTask task = new SaveFileTask(this);
        task.execute();
    }

    private void saveFile(){
        try {
            File fTemp = FileUtils.createEmptyFile(this);
            //mStickerView.save(fTemp);
            mFileSavedpath = fTemp.getAbsolutePath();
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }

    public void noExitClick(View view){
        hideExitPopUp();
    }

    public void yesExitClick(View view){
        if(!showInterstitial()){
            goBackMainActionCategory();
        }
    }

    @Override
    public void onBackPressed() {
        if(mExitPopUp.getVisibility() == View.VISIBLE){
            hideExitPopUp();
            return;
        }

        showExitPopUp();

    }



    class SaveFileTask extends AsyncTask<Void, Void, Void>{
        Context context;
        ProgressDialog pd;

        public SaveFileTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            saveFile();
            return null;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(context, "Please wait", "Image is processing");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if(!showInterstitial()){
                goBackMainActionCategory();
            }
        }
    }
}
