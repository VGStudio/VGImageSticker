package com.app.vgs.vgimagesticker;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.adapter.EffectAdapter;
import com.app.vgs.vgimagesticker.utils.BitmapUtils;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.ImageProcess;
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
    HorizontalScrollView mHoriFilter;

    RelativeLayout mRlAdjust;
    RelativeLayout mRlEffect;
    RelativeLayout mRlFilter;
    RelativeLayout mRlColorEffect;

    SeekBar mSeekSaturation;
    SeekBar mSeekContrast;
    SeekBar mSeekBrightness;
    LinearLayout mLlFilterList;

    RecyclerView mRVEffect;
    RecyclerView mRVColorEffect;

    ImageButton[] mImgFilterList = null;

    EffectAdapter mEffectAdapter;
    EffectAdapter mColorEffectAdapter;

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
        if (!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else if (!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
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
            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
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
        //mHoriEffect = findViewById(R.id.horiEffect);
        mHoriFilter = findViewById(R.id.horiFilter);

        mRlAdjust = findViewById(R.id.rlAdjust);
        mRlEffect = findViewById(R.id.rlEffect);
        mRlFilter = findViewById(R.id.rlFilter);
        mRlColorEffect = findViewById(R.id.rlColorFilter);

        //selectBottomButtonState(mRlAdjust);
        //mLlAdjust.setVisibility(View.VISIBLE);


        mLlFilterList = findViewById(R.id.llFilterList);

        mRVEffect = findViewById(R.id.rvEffect);
        mRVColorEffect = findViewById(R.id.rvColorEffect);

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

    private void unSelectedAllFilterImageButtonState() {
        if (mImgFilterList != null && mImgFilterList.length > 0) {
            for (ImageButton imgButton : mImgFilterList) {
                selectEffectFilterStateView(imgButton, false);
            }
        }
    }

    private void selectEffectFilterStateView(View view, boolean selected) {
        if (selected) {
            view.setBackgroundResource(R.drawable.bg_button_shape);
        } else {
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

    private void hideAllEditImageTool(){
        mLlAdjust.setVisibility(View.GONE);
        mRVEffect.setVisibility(View.GONE);
        mRVColorEffect.setVisibility(View.GONE);
        mHoriFilter.setVisibility(View.GONE);
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
        int visibility = mHoriFilter.getVisibility();
        hideAllEditImageTool();
        if (visibility == View.VISIBLE) {
            unSelectedBottomButtonState();
        } else {
            selectBottomButtonState(mRlFilter);
            mHoriFilter.setVisibility(View.VISIBLE);
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


    private void getEffectIcon() {
        try {
            ImageProcess imageProcess = new ImageProcess();

            Bitmap bitmapTemp = null;

            LogUtils.d("Start create round icon");
            Bitmap bitmap = getIcon();
            Bitmap roundBitmap = imageProcess.applyRoundCornerEffect(bitmap, 45.0f);
            FileUtils.saveBitmapToFile(roundBitmap, "effect_icon", "icon_effect_round.png");
            LogUtils.d("Finish create round icon");

            LogUtils.d("Start create black dots icon");
            bitmap = getIcon();
            Bitmap blackDots = imageProcess.applyBlackFilter(bitmap);
            FileUtils.saveBitmapToFile(blackDots, "effect_icon", "icon_effect_blackdots.png");
            LogUtils.d("Finish create black dots icon");

            LogUtils.d("Start create snow icon");
            bitmap = getIcon();
            Bitmap snowBitmap = imageProcess.applySnowEffect(bitmap);
            FileUtils.saveBitmapToFile(snowBitmap, "effect_icon", "icon_effect_snow.png");
            LogUtils.d("Finish create snow icon");

            LogUtils.d("Start create tint icon");
            bitmap = getIcon();
            Bitmap tintBitmap = imageProcess.applyTintEffect(bitmap, 100);
            FileUtils.saveBitmapToFile(tintBitmap, "effect_icon", "icon_effect_tint.png");
            LogUtils.d("Finish create tint icon");

            LogUtils.d("Start create green icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.GREEN);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_green.png");
            LogUtils.d("Finish create green icon");


            LogUtils.d("Start create cyan icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.CYAN);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_cyan.png");
            LogUtils.d("Finish create cyan icon");

            LogUtils.d("Start create YELLOW icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.YELLOW);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_yellow.png");
            LogUtils.d("Finish create YELLOW icon");

            LogUtils.d("Start create blue icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.BLUE);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_blue.png");
            LogUtils.d("Finish create blue icon");

            LogUtils.d("Start create gray icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.GRAY);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_gray.png");
            LogUtils.d("Finish create gray icon");

            LogUtils.d("Start create magenta icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.MAGENTA);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_magenta.png");
            LogUtils.d("Finish create magenta icon");

            LogUtils.d("Start create red icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.RED);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_red.png");
            LogUtils.d("Finish create red icon");

            LogUtils.d("Start create indigo icon");
            Drawable drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#283593"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_indigo.png");
            LogUtils.d("Finish create indigo icon");

            LogUtils.d("Start create Purple icon");
            drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#D500F9"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_purple.png");
            LogUtils.d("Finish create Purple icon");

            LogUtils.d("Start create Orange icon");
            drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_orange.png");
            LogUtils.d("Finish create Orange icon");

            LogUtils.d("Start create Teal icon");
            drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#004D40"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_teal.png");
            LogUtils.d("Finish create Teal icon");

            LogUtils.d("Start create Lime icon");
            drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#C0CA33"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_lime.png");
            LogUtils.d("Finish create Lime icon");

            LogUtils.d("Start create Pink icon");
            drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#E91E63"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_pink.png");
            LogUtils.d("Finish create Pink icon");

            LogUtils.d("Start create Deep Purple icon");
            drawable = getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#6200EA"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_deeppurple.png");
            LogUtils.d("Finish create Deep Purple icon");


        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }


    private void getColorEffectIcon() {
        try {
            ImageProcess imgFilter = new ImageProcess();
            String folder = "color_effect_icon";

            Bitmap bitmapTemp = null;
            LogUtils.d("Start");
            Bitmap bitmap = getIcon();

            bitmapTemp = imgFilter.applyBoostEffect(bitmap, 1, 40);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_boostred.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyBoostEffect(bitmap, 2, 30);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_boostgreen.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyBoostEffect(bitmap, 3, 67);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_boostblue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyBrightnessEffect(bitmap, 80);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_brightness.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyColorFilterEffect(bitmap, 255, 0, 0);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_colorred.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyColorFilterEffect(bitmap, 0, 255, 0);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_colorgreen.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyColorFilterEffect(bitmap, 0, 0, 255);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_colorblue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyDecreaseColorDepthEffect(bitmap, 64);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_paintdeep.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyDecreaseColorDepthEffect(bitmap, 32);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_paintlight.png");


            bitmap = getIcon();
            bitmapTemp = imgFilter.applyContrastEffect(bitmap, 25);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_contrast.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyGammaEffect(bitmap, 1.8, 1.8, 1.8);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_gamma.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyGreyscaleEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_grayscale.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyHueFilter(bitmap, 2);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_hue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyInvertEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_invert.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyMeanRemovalEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_mean.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySepiaToningEffect(bitmap, 10, 1.5, 0.6, 0.12);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_sepia.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySepiaToningEffect(bitmap, 10, 0.88, 2.45, 1.43);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_sepiagreen.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySepiaToningEffect(bitmap, 10, 1.2, 0.87, 2.1);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_sepiablue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyEmbossEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_emboss.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyEngraveEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_engrave.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyGaussianBlurEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_gaussianblur.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySmoothEffect(bitmap, 100);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_smooth.png");


            LogUtils.d("Finish");


        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private Bitmap getIcon() {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inMutable = true;
        return BitmapFactory.decodeResource(getResources(), R.drawable.filter__0, option);
    }


    @Override
    public void onBackPressed() {
        int editToolVisible = mLlAdjust.getVisibility() +  mRVEffect.getVisibility() + mRVColorEffect.getVisibility() + mHoriFilter.getVisibility();
        if(editToolVisible < View.GONE * 4){
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
            getOrizinalImage();
            if (mBitmapEdit != null) {
                Bitmap bitmap = null;
                if (mode == EffectAdapter.MODE_EFFECT) {
                    bitmap = BitmapUtils.applyEffectImage(context, mBitmapEdit, index);
                } else if (mode == EffectAdapter.MODE_COLOR_EFFECT) {
                    bitmap = BitmapUtils.applyColorEffectImage(mBitmapEdit, index);
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
                mEffectAdapter.notifyDataSetChanged();
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
