package com.app.vgs.vgimagesticker;

import android.Manifest;
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

import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class CropImageActivity extends BaseActivity {
    public static final int REQUEST_IMAGE_FROM_GALLERY = 10001;
    public static final String IMAGE_SELECTED_URI = "IMAGE_SELECTED_URI";


    private CropImageView mCropImageView;
    private Button mFreeSizeBtn;
    private Button mSquareBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        initView();
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

        mFreeSizeBtn.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mSquareBtn.setBackgroundColor(getResources().getColor(R.color.button_default));
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
        String str = String.format("%s_%d.png", new Object[] { "img_sticker", System.currentTimeMillis() }).toString();
        LogUtils.d(str);

    }

    public void doneClick(View view){

        saveImageCroped();


    }

    private void saveImageCroped(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            Bitmap bitmap = mCropImageView.getCroppedImage();
            FileUtils.saveBitmap(bitmap, this);
        }
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
}
