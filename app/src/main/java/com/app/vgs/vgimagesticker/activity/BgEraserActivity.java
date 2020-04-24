package com.app.vgs.vgimagesticker.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.app.vgs.vgimagesticker.view.cutout.DrawView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class BgEraserActivity extends BaseActivity {
    private String mFileSavedpath = null;

    DrawView mDrawView;
    Bitmap mBitmap;
    ImageButton mUndo, mRedo;
    FrameLayout mLoadingModal;
    GestureFrameLayout mGestureView;
    RelativeLayout mRlZoom, mRlManuEraser, mRlAutoEraser;
    SeekBar mSeekBrushSize;
    RelativeLayout mRlExitPopUp;
    AdView mBannerAdView;

    private static final float MAX_ZOOM = 4F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_eraser);
        initView();
        setListener();
        initAds();
        initData();
    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = false;
    }

    private void setListener() {
        mSeekBrushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mDrawView.setStrokeWidth(i);
                mDrawView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initData() {
        mFileSavedpath = getIntent().getStringExtra(MainActionActivity.KEY_IMAGE_PATH_UPDATE);

        mDrawView.setButtons(mUndo, mRedo);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        mBitmap = BitmapFactory.decodeFile(mFileSavedpath, options);
        mDrawView.setBitmap(mBitmap);
    }

    private void initView() {
        mRlZoom = findViewById(R.id.rlZoom);
        mRlManuEraser = findViewById(R.id.rlManuEraser);
        mRlAutoEraser = findViewById(R.id.rlAutoEraser);
        mSeekBrushSize = findViewById(R.id.seekBrushSize);
        mRlExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);

        mDrawView = findViewById(R.id.drawView);
        mUndo = findViewById(R.id.undo);
        mRedo = findViewById(R.id.redo);
        mLoadingModal = findViewById(R.id.loadingModal);

        mDrawView.setDrawingCacheEnabled(true);
        mDrawView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mDrawView.setAction(DrawView.DrawViewAction.MANUAL_CLEAR);
        mDrawView.setStrokeWidth(40);

        mLoadingModal.setVisibility(View.GONE);
        mDrawView.setLoadingModal(mLoadingModal);


        mGestureView = findViewById(R.id.gestureView);

        manuEraserClick(null);
    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void deactivateGestureView() {
        mGestureView.getController().getSettings()
                .setPanEnabled(false)
                .setZoomEnabled(false)
                .setDoubleTapEnabled(false);
    }

    private void activateGestureView() {
        mGestureView.getController().getSettings()
                .setMaxZoom(MAX_ZOOM)
                .setDoubleTapZoom(-1f) // Falls back to max zoom level
                .setPanEnabled(true)
                .setZoomEnabled(true)
                .setDoubleTapEnabled(true)
                .setOverscrollDistance(0f, 0f)
                .setOverzoomFactor(2f);
    }

    public void unClickBottomButton(){
        mRlZoom.setBackgroundColor(getResources().getColor(R.color.button_default));
        mRlManuEraser.setBackgroundColor(getResources().getColor(R.color.button_default));
        mRlAutoEraser.setBackgroundColor(getResources().getColor(R.color.button_default));
    }

    public void undoClick(View view){
        mDrawView.undo();
    }

    public void redoClick(View view){
        mDrawView.redo();
    }

    public void zoomClick(View view){
        activateGestureView();
        unClickBottomButton();
        mRlZoom.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mDrawView.setAction(DrawView.DrawViewAction.ZOOM);
    }

    public void manuEraserClick(View view){
        deactivateGestureView();
        unClickBottomButton();
        mRlManuEraser.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mDrawView.setAction(DrawView.DrawViewAction.MANUAL_CLEAR);
    }
    public void autoEraserClick(View view){
        deactivateGestureView();
        unClickBottomButton();
        mRlAutoEraser.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mDrawView.setAction(DrawView.DrawViewAction.AUTO_CLEAR);
    }

    public void backClick(View view){
        showExitPopUp();
    }

    public void noExitClick(View view) {
        hideExitPopUp();
    }

    public void yesExitClick(View view) {
        goBackMainActionCategory();
    }

    public void onSaveClick(View view){
        try {

            Bitmap bitmap = mDrawView.getDrawingCache();
            File fTemp = FileUtils.saveBitmapToFile(bitmap, Const.TEMP_FOLDER, Const.TEMP_IMAGE_FILE);
            mFileSavedpath = fTemp.getAbsolutePath();
            goBackMainActionCategory();
            LogUtils.d(mFileSavedpath);
        }catch (Exception exp){
            LogUtils.e(exp);
        }

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

    @Override
    public void onBackPressed() {
        if (mRlExitPopUp.getVisibility() == View.VISIBLE) {
            hideExitPopUp();
            return;
        }
        showExitPopUp();
    }
}
