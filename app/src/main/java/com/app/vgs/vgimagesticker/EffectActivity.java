package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class EffectActivity extends BaseActivity {
    public static String KEY_IMAGE_PATH = "KEY_IMAGE_PATH";


    private String mFileSavedpath = null;
    private Bitmap mBitmapEdit = null;


    RelativeLayout mRlHeader;
    ImageView mIvPreview;
    View mExitPopUp;
    AdView mBannerAdView;

    LinearLayout mLlAdjust;
    HorizontalScrollView mHoriEffect;
    HorizontalScrollView mHoriFilter;
    HorizontalScrollView mHoriColorEffect;

    RelativeLayout mRlAdjust;
    RelativeLayout mRlEffect;
    RelativeLayout mRlFilter;
    RelativeLayout mRlColorEffect;

    SeekBar mSeekSaturation;
    SeekBar mSeekContrast;
    SeekBar mSeekBrightness;


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
        if (mBitmapEdit != null) {
            mBitmapEdit.recycle();
        }
        mBitmapEdit = null;
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

        mBitmapEdit = BitmapFactory.decodeResource(getResources(), R.drawable.gai_xinh3);
        mIvPreview.setImageBitmap(mBitmapEdit);
        //mSeekSaturation.setProgress(255);
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
        mHoriEffect = findViewById(R.id.horiEffect);
        mHoriFilter = findViewById(R.id.horiFilter);
        mHoriColorEffect = findViewById(R.id.horiColorEffect);

        mRlAdjust = findViewById(R.id.rlAdjust);
        mRlEffect = findViewById(R.id.rlEffect);
        mRlFilter = findViewById(R.id.rlFilter);
        mRlColorEffect = findViewById(R.id.rlColorFilter);

        selectBottomButtonState(mRlAdjust);
        mLlAdjust.setVisibility(View.VISIBLE);

        mSeekSaturation = findViewById(R.id.seekSaturation);
        mSeekContrast = findViewById(R.id.seekContrast);
        mSeekBrightness = findViewById(R.id.seekBrightness);

        mSeekBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                float brightness = progress / 2.0f;
                mBitmapEdit = changeBitmapContrastBrightness(mBitmapEdit, 1.0f, brightness);
                if (mBitmapEdit != null) {
                    mIvPreview.setImageBitmap(mBitmapEdit);
                }
                LogUtils.d("progress: " + progress + "  == brightness:" + brightness);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekBrightness.setProgress(100);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


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

        mLlAdjust.setVisibility(View.GONE);
        mHoriEffect.setVisibility(View.GONE);
        mHoriFilter.setVisibility(View.GONE);
        mHoriColorEffect.setVisibility(View.GONE);
    }

    private void selectBottomButtonState(View view) {
        unSelectedBottomButtonState();
        view.setBackgroundColor(getResources().getColor(R.color.button_focused));
    }

    public Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
        try {
//            ColorMatrix cm = new ColorMatrix(new float[]
//                    {
//                            contrast, 0, 0, 0, brightness,
//                            0, contrast, 0, 0, brightness,
//                            0, 0, contrast, 0, brightness,
//                            0, 0, 0, 1, 0
//                    });
            ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                    contrast, 0.0F, 0.0F, 0.0F, brightness, 0.0F, contrast, 0.0F, 0.0F, brightness,
                    0.0F, 0.0F, contrast, 0.0F, brightness, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F});

            Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

            Canvas canvas = new Canvas(bitmap);

            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(bmp, 0, 0, paint);
            return bitmap;
        } catch (Exception exp) {
            LogUtils.e(exp);
            return null;
        }

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


    public void adJustClick(View view) {
        if (mLlAdjust.getVisibility() == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlAdjust);
            mLlAdjust.setVisibility(View.VISIBLE);
        }
    }

    public void effectClick(View view) {
        if (mHoriEffect.getVisibility() == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlEffect);
            mHoriEffect.setVisibility(View.VISIBLE);
        }
    }

    public void filterClick(View view) {
        if (mHoriFilter.getVisibility() == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlFilter);
            mHoriFilter.setVisibility(View.VISIBLE);
        }
    }

    public void colorFilterClick(View view) {
        if (mHoriColorEffect.getVisibility() == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlColorEffect);
            mHoriColorEffect.setVisibility(View.VISIBLE);
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
        if (mExitPopUp.getVisibility() == View.VISIBLE) {
            hideExitPopUp();
            return;
        }

        showExitPopUp();

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
