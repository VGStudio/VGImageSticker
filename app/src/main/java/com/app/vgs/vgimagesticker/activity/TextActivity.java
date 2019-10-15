package com.app.vgs.vgimagesticker.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.adapter.StickerAdapter;
import com.app.vgs.vgimagesticker.utils.BitmapUtils;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.app.vgs.vgimagesticker.utils.ScreenDimension;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.markushi.ui.CircleButton;

public class TextActivity extends AppCompatActivity {


    LinearLayout mLLStickerGroup;
    public static String KEY_GROUP_STICKER_ID = "KEY_GROUP_STICKER_ID";


    private String mFileSavedpath = null;

    GridView mGvTextSticker;
    RelativeLayout mRlHeader;
    StickerView mStickerView;
    ImageView mIvPreview;
    HorizontalScrollView mColorListFilterView;
    View mExitPopUp;
    AdView mBannerAdView;
    View mRlColorFilter;
    RelativeLayout mRlStickerLayout;

    RelativeLayout mRlTextSticker;
    RelativeLayout mRlText;
    LinearLayout mLlEditText;



    HorizontalScrollView mHrTextColor;
    HorizontalScrollView mHrTextFont;
    LinearLayout mLlTextFormat;
    SeekBar mSkTextSize;


    //======text format control=======//
    TextView mTvTextFormat;
    ImageButton mIbTextFormat;

    ImageButton mIbTextColor;
    TextView mTvTextColor;

    ImageButton mIbTextFont;
    TextView mTvTextFont;

    ImageButton mIbTextSize;
    TextView mTvTextSize;

    ImageButton mIbTextShadow;
    TextView mTvTextShadow;

    //================================//


    View.OnClickListener onColorFilterClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Sticker selectedSticker = mStickerView.getHandlingSticker();
            if (selectedSticker != null) {
                String colorCode = view.getTag().toString();
                Drawable d = selectedSticker.getDrawable();
                d.setColorFilter(Color.parseColor(colorCode), PorterDuff.Mode.MULTIPLY);
                mStickerView.invalidate();
                LogUtils.d(colorCode);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        initView();
        initListener();
        initData();


    }

    private void initListener() {
        mIvPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideEditSticker();

            }
        });
    }


    private void initData() {
        initAds();
        initTextSticker();
        initTextColorListView();
        initTextFontsListView();

        //mFileSavedpath = getIntent().getStringExtra(MainActionActivity.KEY_IMAGE_PATH_UPDATE);
//        if(mFileSavedpath != null){
//            mIvPreview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    if (Build.VERSION.SDK_INT >= 16) {
//                        mIvPreview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    } else {
//                        mIvPreview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    }
//
//                    int width = mRlStickerLayout.getWidth();
//                    int height = mRlStickerLayout.getHeight();
//                    Bitmap bitmap = BitmapFactory.decodeFile(mFileSavedpath);
//                    bitmap = BitmapUtils.resizeBitmap(bitmap, width, height);
//                    mIvPreview.setImageBitmap(bitmap);
//                    mIvPreview.invalidate();
//                }
//            });
//
//        }


        //mColorListForFilter = JsonUtils.getColorListFromJson(this);
        //initColorFilterView();
    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void initView() {
        mGvTextSticker = findViewById(R.id.gvTextSticker);
        mStickerView = findViewById(R.id.sticker_view);
        mExitPopUp = findViewById(R.id.exitPopUp);

        mBannerAdView = findViewById(R.id.bannerAdView);
        mRlTextSticker = findViewById(R.id.rlTextSticker);
        mRlText = findViewById(R.id.rlText);

        mIvPreview = findViewById(R.id.ivPreview);

        mLlEditText = findViewById(R.id.llEditText);


        mHrTextColor = findViewById(R.id.hrTextColor);
        mHrTextFont = findViewById(R.id.hrTextFont);
        mLlTextFormat = findViewById(R.id.llTextFormat);
        mSkTextSize = findViewById(R.id.skTextSize);


        mTvTextFormat = findViewById(R.id.tvTextFormat);
        mIbTextFormat = findViewById(R.id.ibTextFormat);

        mIbTextColor = findViewById(R.id.ibTextColor);
        mTvTextColor = findViewById(R.id.tvTextColor);

        mIbTextFont = findViewById(R.id.ibTextFont);
        mTvTextFont = findViewById(R.id.tvTextFont);

        mIbTextSize = findViewById(R.id.ibTextSize);
        mTvTextSize = findViewById(R.id.tvTextSize);

        mIbTextShadow = findViewById(R.id.ibTextShadow);
        mTvTextShadow = findViewById(R.id.tvTextShadow);


//        mLLStickerGroup = findViewById(R.id.llStickerGroup);
//        mRlHeader = findViewById(R.id.rlHeader);

//        mLlEditSticker = findViewById(R.id.llEditSticker);
//        mColorListFilterView = findViewById(R.id.colorListFilterView);

//
//        mRlColorFilter = findViewById(R.id.rlColorFilter);
//        mRlStickerLayout = findViewById(R.id.rlStickerLayout);


        unSelectedEditText();
        initStickerView();
    }

    private void initTextColorListView() {
        try {

            ImageButton ibTextColor = findViewById(R.id.ibTextColor);
            ibTextColor.setColorFilter(getResources().getColor(R.color.unselected));


            //TEXT_COLOR_LIST_DATA_PATH
            List<String> lstTextColorList = JsonUtils.getColorListFromJson(this, Const.TEXT_COLOR_LIST_DATA_PATH);

            int size40dip = getResources().getDimensionPixelSize(R.dimen.size_40dip);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);


            LinearLayout.LayoutParams childLayoutParam = new LinearLayout.LayoutParams(size40dip, size40dip);
            for (final String str : lstTextColorList) {
                CircleButton circleButton = new CircleButton(this);
                circleButton.setLayoutParams(childLayoutParam);
                circleButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                int color = Color.parseColor(str);
                circleButton.setColor(color);

                circleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TextActivity.this, str, Toast.LENGTH_SHORT).show();
                    }
                });
                linearLayout.addView(circleButton);
            }
            mHrTextColor.addView(linearLayout);


        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private void initTextFontsListView(){
        try {

            int size40dip = getResources().getDimensionPixelSize(R.dimen.size_40dip);
            int size10dip = getResources().getDimensionPixelSize(R.dimen.size_10dip);


            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, size40dip);
            linearLayout.setLayoutParams(layoutParams);


            LinearLayout.LayoutParams childLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, size40dip);
            childLayoutParam.setMargins(size10dip, 0, 0, 0);


            String [] lstFonts = getAssets().list(Const.FONTS_FOLDER_PATH);
            int textColor = getResources().getColor(R.color.colourBgAndSelected);
            int textSize = ScreenDimension.spToPx(10, this);


            for (String fontName : lstFonts){
                String fontPath = Const.FONTS_FOLDER_PATH + "/" + fontName;
                Typeface font = Typeface.createFromAsset(getAssets(), fontPath);
                TextView tv = new TextView(this);
                tv.setLayoutParams(childLayoutParam);
                tv.setText("Fonts");
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(textSize);
                tv.setTextColor(textColor);
                tv.setTypeface(font);
                linearLayout.addView(tv);

            }

            mHrTextFont.addView(linearLayout);
        }catch (Exception exp){
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


    private void initStickerView() {
        try {

            BitmapStickerIcon deleteIcon = new BitmapStickerIcon(getResources().getDrawable(
                    com.xiaopo.flying.sticker.R.drawable.sticker_ic_close_white_18dp),
                    BitmapStickerIcon.LEFT_TOP);
            deleteIcon.setIconEvent(new DeleteIconEvent());

            BitmapStickerIcon zoomIcon = new BitmapStickerIcon(getResources().getDrawable(
                    com.xiaopo.flying.sticker.R.drawable.sticker_ic_scale_white_18dp),
                    BitmapStickerIcon.RIGHT_BOTOM);
            zoomIcon.setIconEvent(new ZoomIconEvent());

            BitmapStickerIcon flipIcon = new BitmapStickerIcon(getResources().getDrawable(
                    com.xiaopo.flying.sticker.R.drawable.sticker_ic_flip_white_18dp),
                    BitmapStickerIcon.RIGHT_TOP);
            flipIcon.setIconEvent(new FlipHorizontallyEvent());


            mStickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon));
            mStickerView.setLocked(false);
            mStickerView.setConstrained(true);


        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }

    private void selectedEditTextView(ImageButton button, TextView textView){
        try {
            unSelectedEditText();
            int colorSelected = getResources().getColor(R.color.colourBgAndSelected);
            button.setColorFilter(colorSelected);
            textView.setTextColor(colorSelected
            );
        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }
    private void unSelectedEditText(){
        try {
            int unSelectColor = getResources().getColor(R.color.unselected);

            mTvTextFormat.setTextColor(unSelectColor);
            mIbTextFormat.setColorFilter(unSelectColor);

            mIbTextColor.setColorFilter(unSelectColor);
            mTvTextColor.setTextColor(unSelectColor);

            mIbTextFont.setColorFilter(unSelectColor);
            mTvTextFont.setTextColor(unSelectColor);

            mIbTextSize.setColorFilter(unSelectColor);
            mTvTextSize.setTextColor(unSelectColor);

            mIbTextShadow.setColorFilter(unSelectColor);
            mTvTextShadow.setTextColor(unSelectColor);

            mHrTextColor.setVisibility(View.GONE);
            mHrTextFont.setVisibility(View.GONE);
            mLlTextFormat.setVisibility(View.GONE);
        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }


    private void hideEditSticker() {
        mStickerView.setHandlingSticker(null);
        mStickerView.invalidate();

    }

    public void hideGroupSticker() {
        mGvTextSticker.setVisibility(View.GONE);
        //unSelecteStickerGroupButtonState();
        //mRlHeader.setVisibility(View.VISIBLE);
    }

    private void showExitPopUp() {
        mExitPopUp.setVisibility(View.VISIBLE);
    }

    private void hideExitPopUp() {
        mExitPopUp.setVisibility(View.GONE);
    }


    public void closeEditStickerClick(View view) {
        hideEditSticker();
    }


    public void showTextStickerClick(View view) {
        modeAddSticker(true);
    }

    public void showText(View view) {
        mRlText.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        mLlEditText.setVisibility(View.VISIBLE);
        modeAddSticker(false);
    }

    private void modeAddSticker(boolean isShow) {
        if (!isShow) {
            mGvTextSticker.setVisibility(View.GONE);
            mRlTextSticker.setBackgroundColor(getResources().getColor(R.color.button_default));
        } else {
            mLlEditText.setVisibility(View.GONE);
            mGvTextSticker.setVisibility(View.VISIBLE);
            mRlTextSticker.setBackgroundColor(getResources().getColor(R.color.button_pressed));
            mRlText.setBackgroundColor(getResources().getColor(R.color.button_default));
        }
    }

    private void initTextSticker() {
        try {
            final List<String> lstStickerPath = JsonUtils.getImagesPathFromJson(this, Const.TEXT_STICKER_DATA_FILE_PATH);
            StickerAdapter stickerAdapter = new StickerAdapter(this, lstStickerPath);
            mGvTextSticker.setAdapter(stickerAdapter);

            mGvTextSticker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        String path = lstStickerPath.get(i);
                        hideGroupSticker();
                        Drawable d = Drawable.createFromStream(getResources().getAssets().open(path), null);
                        DrawableSticker sticker = new DrawableSticker(d);
                        mStickerView.addSticker(sticker);
                        mGvTextSticker.setVisibility(View.GONE);
                        mRlTextSticker.setBackgroundColor(getResources().getColor(R.color.button_default));
                    } catch (Exception exp) {
                        LogUtils.e(exp);
                    }
                }
            });
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }


    public void saveImageClick(View view) {
        SaveFileTask task = new SaveFileTask(this);
        task.execute();
    }

    private void saveFile() {
        try {


            File fTemp = FileUtils.createEmptyFile(this);
            //Bitmap bitmap = mStickerView.getDrawingCache();
            //File fTemp = FileUtils.saveBitmapToFile(bitmap, Const.TEMP_FOLDER, Const.TEMP_IMAGE_FILE);
            mStickerView.save(fTemp);
            mFileSavedpath = fTemp.getAbsolutePath();

        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }

    public void textColorListClick(View view) {
        selectedEditTextView(mIbTextColor, mTvTextColor);
        mHrTextColor.setVisibility(View.VISIBLE);
    }

    public void textFontListClick(View view) {
        selectedEditTextView(mIbTextFont, mTvTextFont);
        mHrTextFont.setVisibility(View.VISIBLE);
    }

    public void textFormatListClick(View view){
        selectedEditTextView(mIbTextFormat, mTvTextFormat);
        mLlTextFormat.setVisibility(View.VISIBLE);
    }

    public void textSizeClick(View view){
        selectedEditTextView(mIbTextSize, mTvTextSize);
        mSkTextSize.setVisibility(View.VISIBLE);
    }

    public void noExitClick(View view) {
        hideExitPopUp();
    }

    public void yesExitClick(View view) {
        goBackMainActionCategory();
    }

    @Override
    public void onBackPressed() {
        if (mExitPopUp.getVisibility() == View.VISIBLE) {
            hideExitPopUp();
            return;
        }
        if (mGvTextSticker.getVisibility() == View.VISIBLE) {
            hideGroupSticker();
            return;
        }

//        if(mLlEditSticker.getVisibility() == View.VISIBLE){
//            hideEditSticker();
//            return;
//        }
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
            pd = ProgressDialog.show(context, getResources().getString(R.string.please_wait), getResources().getString(R.string.image_saving));
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            goBackMainActionCategory();
        }
    }
}
