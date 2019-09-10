package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.utils.BitmapUtils;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.ImageProcess;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;

public class EffectActivity extends BaseActivity {
    public static String KEY_IMAGE_PATH = "KEY_IMAGE_PATH";


    private String mFileSavedpath = null;
    private Bitmap mBitmapEdit = null;
    private Bitmap mBitmapInput = null;


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
    LinearLayout mLlFilterList;

    ImageButton [] mImgFilterList = null;

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
        if(mBitmapEdit != null){
            mBitmapEdit.recycle();
            mBitmapEdit = null;
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


        mBitmapInput = BitmapFactory.decodeResource(getResources(), R.drawable.gai_xinh3);
        mIvPreview.setImageBitmap(mBitmapInput);

        initFilterListView();
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

        mLlFilterList = findViewById(R.id.llFilterList);

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

    public void getOrizinalImage() {
        try {
            mIvPreview.setImageBitmap(mBitmapInput);
            Drawable drawable = mIvPreview.getDrawable();

            mBitmapEdit = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
            drawable.draw(new Canvas(mBitmapEdit));

        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }

    private void initFilterListView() {
        try {

            LayoutInflater layoutInflater = getLayoutInflater();

            mLlFilterList.setWeightSum(BitmapUtils.FILTER_LIST.length);
            mImgFilterList = new ImageButton[BitmapUtils.FILTER_LIST.length];
            for (int i = 0; i < BitmapUtils.FILTER_LIST.length; i++) {
                getOrizinalImage();
                String filterName = BitmapUtils.FILTER_LIST[i];
                final View view = layoutInflater.inflate(R.layout.layout_filter_item, mLlFilterList, false);
                TextView textView = view.findViewById(R.id.tvName);
                ImageButton imageIcon = view.findViewById(R.id.ibIcon);
                mImgFilterList[i] = imageIcon;

                BitmapFactory.Options option = new BitmapFactory.Options();
                option.inMutable = true;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.filter__0, option);
                bitmap = BitmapUtils.filterImage(this, bitmap, i);
                imageIcon.setImageBitmap(bitmap);
                imageIcon.setTag(i);
                imageIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index = Integer.parseInt(view.getTag().toString());
                        getOrizinalImage();
                        unSelectedAllFilterImageButtonState();
                        Bitmap bitmap = BitmapUtils.filterImage(getBaseContext(), mBitmapEdit, index);
                        selectEffectFilterStateView(view, true);
                        mIvPreview.setImageBitmap(bitmap);
                    }
                });

                textView.setText(filterName);
                mLlFilterList.addView(view);
            }




        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private void unSelectedAllFilterImageButtonState(){
        if(mImgFilterList != null && mImgFilterList.length > 0){
            for(ImageButton imgButton : mImgFilterList){
                selectEffectFilterStateView(imgButton, false);
            }
        }
    }

    private void selectEffectFilterStateView(View view, boolean selected){
        if(selected){
            view.setBackgroundResource(R.drawable.bg_button_shape);
        }else{
            view.setBackgroundResource(R.color.button_default);
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

        mLlAdjust.setVisibility(View.GONE);
        mHoriEffect.setVisibility(View.GONE);
        mHoriFilter.setVisibility(View.GONE);
        mHoriColorEffect.setVisibility(View.GONE);
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


    public void adJustClick(View view) {
        if (mLlAdjust.getVisibility() == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlAdjust);
            mLlAdjust.setVisibility(View.VISIBLE);
        }
    }

    public void effectClick(View view) {
        LogUtils.d("start");
        getOrizinalImage();
        ImageProcess imageProcess = new ImageProcess();
        //Bitmap bitmap = imageProcess.applyColorFilterEffect(mBitmapEdit, 4, 6, 8);
        //Drawable drawable = getResources().getDrawable(R.drawable.gai_xinh3);
        //drawable.setColorFilter(Color.parseColor("#6200EA"), PorterDuff.Mode.MULTIPLY);
        Bitmap bitmap = imageProcess.applyGreyscaleEffect(mBitmapEdit);
        mIvPreview.setImageBitmap(bitmap);
        //mIvPreview.setImageDrawable(drawable);
        LogUtils.d("end1");


//        if (mHoriEffect.getVisibility() == View.VISIBLE) {
//            unSelectedBottomButtonState();
//        } else {
//            selectBottomButtonState(mRlEffect);
//            mHoriEffect.setVisibility(View.VISIBLE);
//        }
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

    class FilterImageTask extends AsyncTask<Void, Void, Bitmap> {
        Context context;
        View viewClicked;
        ProgressDialog pd;
        int indexFilterSelected;

        public FilterImageTask(Context ctx, View view, int index) {
            context = ctx;
            this.viewClicked  = view;
            indexFilterSelected = index;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            getOrizinalImage();
            Bitmap bitmap = BitmapUtils.filterImage(getBaseContext(), mBitmapEdit, indexFilterSelected);
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            unSelectedAllFilterImageButtonState();
            pd = ProgressDialog.show(context, context.getString(R.string.label_please_wait), context.getString(R.string.image_processing));
            selectEffectFilterStateView(viewClicked, true);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                mIvPreview.setImageBitmap(bitmap);
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
