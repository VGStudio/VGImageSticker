package com.app.vgs.vgimagesticker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.app.vgs.vgimagesticker.vo.DrawingView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class BlurActivity extends BaseActivity {
    private String mFileSavedpath = null;

    DrawingView mDrawingView;
    SeekBar mSeekBarBlur, mSeekBrushSize;
    RelativeLayout mRlExitPopUp;
    AdView mBannerAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        initView();
        setListener();
        initAds();
        initData();

    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = false;
    }


    private void initData() {
        mFileSavedpath = getIntent().getStringExtra(MainActionActivity.KEY_IMAGE_PATH_UPDATE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(mFileSavedpath, options);
        mDrawingView.setBitmap(bitmap);
    }



    private void initView() {
        mDrawingView = findViewById(R.id.drawing);
        mSeekBarBlur = findViewById(R.id.blurseekbar);
        mSeekBrushSize = findViewById(R.id.seekBrushSize);
        mRlExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void setListener(){
        mSeekBarBlur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mDrawingView.setBlurProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekBrushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mDrawingView.setBrushSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onBackClick(View view){
        showExitPopUp();
    }

    public void onSaveClick(View view){
        try {
            Bitmap bitmap = mDrawingView.getBitmap();
            File fTemp = FileUtils.saveBitmapToFile(bitmap, Const.TEMP_FOLDER, Const.TEMP_IMAGE_FILE);
            mFileSavedpath = fTemp.getAbsolutePath();
            goBackMainActionCategory();
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }


    @Override
    public void onBackPressed() {
        if (mRlExitPopUp.getVisibility() == View.VISIBLE) {
            hideExitPopUp();
            return;
        }
        showExitPopUp();
    }

    public void noExitClick(View view) {
        hideExitPopUp();
    }

    public void yesExitClick(View view) {
        goBackMainActionCategory();
    }


    @Override
    public void closeInterstitial() {
        goBackMainActionCategory();
    }


    private void goBackMainActionCategory() {
        Intent intent = new Intent();
        if (mFileSavedpath != null) {
            intent.putExtra(MainActionActivity.KEY_IMAGE_PATH_UPDATE, mFileSavedpath);
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
    }

    private void hideExitPopUp() {
        mRlExitPopUp.setVisibility(View.GONE);
    }

    private void showExitPopUp() {
        mRlExitPopUp.setVisibility(View.VISIBLE);
    }






}
