package com.app.vgs.vgimagesticker.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;


public class CropImageActivity extends BaseActivity {
    public static final String IMAGE_SELECTED_URI = "IMAGE_SELECTED_URI";


    private CropImageView mCropImageView;
    private Button mFreeSizeBtn;
    private Button mSquareBtn;

    private View mExitPopUp;

    AdView mBannerAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        initView();
        initAds();
        initData();
    }

    private void initData() {
        Bundle extra = getIntent().getExtras();
        Uri imgUri = Uri.parse(extra.getString(IMAGE_SELECTED_URI));
        mCropImageView.setImageUriAsync(imgUri);
        mCropImageView.setFixedAspectRatio(false);
    }


    private void initView(){
        mCropImageView = findViewById(R.id.cropImageView);
        mFreeSizeBtn = findViewById(R.id.freeSizeBtn);
        mSquareBtn = findViewById(R.id.squareBtn);
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);

        mFreeSizeBtn.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mSquareBtn.setBackgroundColor(getResources().getColor(R.color.button_default));
    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }








    public void squareRationClick(View view) {
        mCropImageView.setFixedAspectRatio(true);
        mFreeSizeBtn.setBackgroundColor(getResources().getColor(R.color.button_default));
        mSquareBtn.setBackgroundColor(getResources().getColor(R.color.button_pressed));
    }
    public void freeSizeRationClick(View view){
        mCropImageView.setFixedAspectRatio(false);
        mFreeSizeBtn.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mSquareBtn.setBackgroundColor(getResources().getColor(R.color.button_default));
    }

    public void rotationClick(View view){
        mCropImageView.rotateImage(90);

    }

    public void doneClick(View view){

        saveImageCroped();


    }

    private void saveImageCroped(){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        getString(R.string.permission_write_storage_rationale),
                        REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
            } else {
                Bitmap bitmap = mCropImageView.getCroppedImage();
                File fileSave = FileUtils.saveBitmapToFile(bitmap, Const.TEMP_FOLDER, Const.TEMP_IMAGE_FILE);
                String fNamePath = fileSave.getAbsolutePath();
                goToMainActivity(fNamePath);
                LogUtils.d(fNamePath);
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }

    private void goToMainActivity(String fSaveFilePath){
        Intent intent = new Intent();
        if(fSaveFilePath != null){
            intent.putExtra(MainActivity.KEY_IMAGE_PATH_UPDATE, fSaveFilePath);
            setResult(Activity.RESULT_OK, intent);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();


    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImageCroped();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = false;
    }
    public void closeInterstitial() {
    }

    public void noExitClick(View view){
        if(mExitPopUp.getVisibility() == View.VISIBLE){
            mExitPopUp.setVisibility(View.GONE);
        }
    }

    public void yesExitClick(View view){
        goToMainActivity(null);
    }

    @Override
    public void onBackPressed() {
        if(mExitPopUp.getVisibility() == View.VISIBLE){
            mExitPopUp.setVisibility(View.GONE);
        }else{
            mExitPopUp.setVisibility(View.VISIBLE);
        }
    }
}
