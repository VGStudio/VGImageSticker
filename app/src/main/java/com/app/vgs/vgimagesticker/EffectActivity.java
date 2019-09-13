package com.app.vgs.vgimagesticker;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.app.vgs.vgimagesticker.adapter.EffectAdapter;
import com.app.vgs.vgimagesticker.utils.BitmapUtils;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.app.vgs.vgimagesticker.vo.EffectItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class EffectActivity extends BaseActivity implements EffectAdapter.EffectChooseListner {
    public static String KEY_IMAGE_PATH = "KEY_IMAGE_PATH";

    private String mFileSavedpath = null;
    private Bitmap mBitmapEdit = null;
    private Bitmap mBitmapInput = null;


    RelativeLayout mRlHeader;
    ImageView mIvPreview;
    View mExitPopUp;
    AdView mBannerAdView;

    LinearLayout mLlAdjust;

    RelativeLayout mRlAdjust;
    RelativeLayout mRlEffect;
    RelativeLayout mRlFilter;
    RelativeLayout mRlColorEffect;

    SeekBar mSeekSaturation;
    SeekBar mSeekContrast;
    SeekBar mSeekBrightness;

    RecyclerView mRVEffect;
    RecyclerView mRVColorEffect;
    RecyclerView mRVFilter;

    EffectAdapter mEffectAdapter;
    EffectAdapter mColorEffectAdapter;
    EffectAdapter mFilterAdapter;

    static {
        System.loadLibrary("NativeImageProcessor");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effects);

        initView();
        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBitmapInput != null) {
            mBitmapInput.recycle();
        }
        mBitmapInput = null;
        if (mBitmapEdit != null) {
            mBitmapEdit.recycle();
            mBitmapEdit = null;
        }
    }

    private void checkAndRequestPermission() {
//        if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
//                    getString(R.string.permission_read_storage_rationale),
//                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
//        } else if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    getString(R.string.permission_write_storage_rationale),
//                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//
//        } else {
//            fillData();
//        }

        if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);

        } else {
            fillData();
        }
    }

    private void fillData() {
        try {
            mBitmapInput = BitmapFactory.decodeResource(getResources(), R.drawable.gai_xinh3);
            if (mBitmapInput != null) {
                mIvPreview.setImageBitmap(mBitmapInput);
            }
            initFilterListView();
            initEffectListView();
            initColorEffectListView();
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fillData();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
        checkAndRequestPermission();


    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void initView() {

        mRlHeader = findViewById(R.id.rlHeader);
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIvPreview = findViewById(R.id.ivPreview);

        mLlAdjust = findViewById(R.id.llAdjust);

        mRlAdjust = findViewById(R.id.rlAdjust);
        mRlEffect = findViewById(R.id.rlEffect);
        mRlFilter = findViewById(R.id.rlFilter);
        mRlColorEffect = findViewById(R.id.rlColorFilter);


        mRVEffect = findViewById(R.id.rvEffect);
        mRVColorEffect = findViewById(R.id.rvColorEffect);
        mRVFilter = findViewById(R.id.rvFilter);

        mSeekSaturation = findViewById(R.id.seekSaturation);
        mSeekContrast = findViewById(R.id.seekContrast);
        mSeekBrightness = findViewById(R.id.seekBrightness);
        mSeekBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float brightness = progress / 2.0f;
                if (mBitmapInput != null) {
                    mIvPreview.setImageBitmap(BitmapUtils.changeBitmapContrastBrightness(mBitmapInput, 1.0f, brightness));
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekContrast.setProgress(100);
                mSeekSaturation.setProgress(256);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mSeekContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float contrast = progress / 100.0f;
                if (mBitmapInput != null) {
                    mIvPreview.setImageBitmap(BitmapUtils.changeBitmapContrastBrightness(mBitmapInput, contrast, 1.0f));
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekBrightness.setProgress(100);
                mSeekSaturation.setProgress(256);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float saturation = progress / 256.0f;
                if (mBitmapInput != null) {
                    mIvPreview.setImageBitmap(BitmapUtils.changeSaturation(mBitmapInput, saturation));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekBrightness.setProgress(100);
                mSeekContrast.setProgress(100);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void getOriginalImage() {
        try {
            if (mBitmapInput != null) {
                mBitmapEdit = mBitmapInput.copy(mBitmapInput.getConfig(), true);
            }
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private void initEffectListView() {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRVEffect.setLayoutManager(horizontalLayoutManager);
        mEffectAdapter = new EffectAdapter(this, EffectItem.getEffectList(), this, EffectAdapter.MODE_EFFECT);
        mRVEffect.setAdapter(mEffectAdapter);

    }

    private void initColorEffectListView() {
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRVColorEffect.setLayoutManager(horizontalLayoutManager);
        mColorEffectAdapter = new EffectAdapter(this, EffectItem.getColorEffectList(), this, EffectAdapter.MODE_COLOR_EFFECT);
        mRVColorEffect.setAdapter(mColorEffectAdapter);

    }

    private void initFilterListView() {
        try {
            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRVFilter.setLayoutManager(horizontalLayoutManager);
            mFilterAdapter = new EffectAdapter(this, EffectItem.getFilterList(), this, EffectAdapter.MODE_FILTER);
            mRVFilter.setAdapter(mFilterAdapter);
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
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


    private void unSelectedBottomButtonState() {
        mRlAdjust.setBackgroundColor(getResources().getColor(R.color.button_default));
        mRlEffect.setBackgroundColor(getResources().getColor(R.color.button_default));
        mRlFilter.setBackgroundColor(getResources().getColor(R.color.button_default));
        mRlColorEffect.setBackgroundColor(getResources().getColor(R.color.button_default));

        hideAllEditImageTool();
    }

    private void selectBottomButtonState(View view) {
        unSelectedBottomButtonState();
        view.setBackgroundColor(getResources().getColor(R.color.button_focused));
    }


    private void showExitPopUp() {
        mExitPopUp.setVisibility(View.VISIBLE);
    }

    private void hideExitPopUp() {
        mExitPopUp.setVisibility(View.GONE);
    }


    private void saveFile() {
        try {
            File fTemp = FileUtils.createEmptyFile(this);
            //mStickerView.save(fTemp);
            mFileSavedpath = fTemp.getAbsolutePath();
        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }

    private void hideAllEditImageTool() {
        mLlAdjust.setVisibility(View.GONE);
        mRVEffect.setVisibility(View.GONE);
        mRVColorEffect.setVisibility(View.GONE);
        mRVFilter.setVisibility(View.GONE);
    }


    public void adJustClick(View view) {
        int visibility = mLlAdjust.getVisibility();
        hideAllEditImageTool();
        if (visibility == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlAdjust);
            mLlAdjust.setVisibility(View.VISIBLE);
        }
    }

    public void effectClick(View view) {
        int visibility = mRVEffect.getVisibility();
        hideAllEditImageTool();
        if (visibility == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlEffect);
            mRVEffect.setVisibility(View.VISIBLE);
        }
    }


    public void colorFilterClick(View view) {
        int visibility = mRVColorEffect.getVisibility();
        hideAllEditImageTool();
        if (visibility == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlColorEffect);
            mRVColorEffect.setVisibility(View.VISIBLE);
        }
    }

    public void filterClick(View view) {
        int visibility = mRVFilter.getVisibility();
        hideAllEditImageTool();
        if (visibility == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlFilter);
            mRVFilter.setVisibility(View.VISIBLE);
        }
    }


    public void saveImageClick(View view) {
        SaveFileTask task = new SaveFileTask(this);
        task.execute();
    }

    public void noExitClick(View view) {
        hideExitPopUp();
    }

    public void yesExitClick(View view) {
        if (!showInterstitial()) {
            goBackMainActionCategory();
        }
    }

    @Override
    public void onBackPressed() {
        int editToolVisible = mLlAdjust.getVisibility() + mRVEffect.getVisibility() + mRVColorEffect.getVisibility() + mRVFilter.getVisibility();
        if (editToolVisible < View.GONE * 4) {
            unSelectedBottomButtonState();
            return;
        }

        if (mExitPopUp.getVisibility() == View.VISIBLE) {
            hideExitPopUp();
            return;
        }
        showExitPopUp();
    }

    @Override
    public void onEffectClick(int position, int mode) {
        EffectImageTask effectImageTask = new EffectImageTask(this, position, mode);
        effectImageTask.execute();
    }

    class EffectImageTask extends AsyncTask<Void, Void, Bitmap> {
        Context context;
        ProgressDialog pd;
        int index;
        int mode;

        public EffectImageTask(Context ctx, int index, int mode) {
            context = ctx;
            this.index = index;
            this.mode = mode;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            getOriginalImage();
            if (mBitmapEdit != null) {
                Bitmap bitmap = null;
                if (mode == EffectAdapter.MODE_EFFECT) {
                    bitmap = BitmapUtils.applyEffectImage(context, mBitmapEdit, index);
                } else if (mode == EffectAdapter.MODE_COLOR_EFFECT) {
                    bitmap = BitmapUtils.applyColorEffectImage(mBitmapEdit, index);
                }else if(mode == EffectAdapter.MODE_FILTER){
                    bitmap = BitmapUtils.applyFilterImage(context, mBitmapEdit, index);
                }
                return bitmap;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(context, context.getString(R.string.label_please_wait), context.getString(R.string.image_processing));
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mIvPreview.setImageBitmap(bitmap);
                if(mode == EffectAdapter.MODE_EFFECT){
                    mEffectAdapter.notifyDataSetChanged();
                }else if(mode == EffectAdapter.MODE_COLOR_EFFECT){
                    mColorEffectAdapter.notifyDataSetChanged();
                }else if(mode == EffectAdapter.MODE_FILTER){
                    mFilterAdapter.notifyDataSetChanged();
                }
            }
            pd.dismiss();
        }
    }


    class SaveFileTask extends AsyncTask<Void, Void, Void> {
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
            if (!showInterstitial()) {
                goBackMainActionCategory();
            }
        }
    }


}
