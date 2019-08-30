package com.app.vgs.vgimagesticker;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.adapter.StickerAdapter;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.util.Arrays;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    LinearLayout mLLStickerGroup;
    List<StickerGroup> mLstStickerGroup;
    public static String KEY_GROUP_STICKER_ID = "KEY_GROUP_STICKER_ID";

    private String mStickerId = "";
    private List<String> mColorListForFilter;

    GridView mGridViewSticker;
    RelativeLayout mRlHeader;
    StickerView mStickerView;
    LinearLayout mLlEditSticker;
    ImageView mIvImage;
    SeekBar mFakeBar;
    HorizontalScrollView mColorListFilterView;

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
        setContentView(R.layout.activity_sticker);

        initView();
        initData();

        addStickerGroupIconView();
    }

    private void initData() {
        mLstStickerGroup = JsonUtils.getStickerGroupFromJsonData(this, "sticker/data.json");
        mColorListForFilter = JsonUtils.getColorListFromJson(this);
        initColorFilterView();
    }

    private void initView() {
        mLLStickerGroup = findViewById(R.id.llStickerGroup);
        mGridViewSticker = findViewById(R.id.gridViewSticker);
        mRlHeader = findViewById(R.id.rlHeader);
        mStickerView = findViewById(R.id.sticker_view);
        mLlEditSticker = findViewById(R.id.llEditSticker);
        mFakeBar = findViewById(R.id.fade_seek);
        mColorListFilterView = findViewById(R.id.colorListFilterView);


        mIvImage = findViewById(R.id.ivImage);

        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("Image click");
                hideEditSticker();

            }
        });

        mFakeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Sticker currentSelectedSticker = mStickerView.getHandlingSticker();
                if (currentSelectedSticker != null) {
                    currentSelectedSticker.setAlpha(progress + 100);
                }
                mStickerView.invalidate();
                LogUtils.d((progress + 100) + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        initStickerView();
    }

    private void initColorFilterView() {
        try {
            mStickerId = getIntent().getStringExtra(KEY_GROUP_STICKER_ID);
            int size = getResources().getDimensionPixelSize(R.dimen.size_50dip);
            int padding = getResources().getDimensionPixelSize(R.dimen.size_4dip);
            LogUtils.d("size:" + size + "   padding:" + padding);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
            linearLayout.setLayoutParams(layoutParams);

            for (String str : mColorListForFilter) {
                Drawable d = Drawable.createFromStream(getAssets().open("color/icon_color.webp"), null);
                ImageButton imageButton = new ImageButton(this);
                imageButton.setLayoutParams(layoutParams);
                imageButton.setPadding(4, 4, 4, 4);
                imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageButton.setBackgroundColor(getResources().getColor(R.color.bar_bg));
                imageButton.setImageDrawable(d);
                imageButton.setTag(str);
                imageButton.setColorFilter(Color.parseColor(str), PorterDuff.Mode.MULTIPLY);
                imageButton.setOnClickListener(onColorFilterClick);
                linearLayout.addView(imageButton);
            }
            mColorListFilterView.addView(linearLayout);


        } catch (Exception exp) {
            LogUtils.e(exp);
        }
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

            mStickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
                @Override
                public void onStickerAdded(@NonNull Sticker sticker) {
                    showEditSticker();
                }

                @Override
                public void onStickerClicked(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerDeleted(@NonNull Sticker sticker) {
                    hideEditSticker();
                }

                @Override
                public void onStickerDragFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerTouchedDown(@NonNull Sticker sticker) {
                    showEditSticker();
                }

                @Override
                public void onStickerZoomFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerFlipped(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerDoubleTapped(@NonNull Sticker sticker) {

                }
            });

        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }

    private void addStickerGroupIconView() {
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            StickerGroup stickerGroup = null;

            for (StickerGroup stkGroup : mLstStickerGroup) {
                if (stkGroup.getId().equals(mStickerId)) {
                    stickerGroup = stkGroup;
                }
            }

            mLLStickerGroup.setWeightSum(stickerGroup.getLstSubGroup().size());


            for (StickerSubGroup subGroup : stickerGroup.getLstSubGroup()) {
                final View view = layoutInflater.inflate(R.layout.layout_sub_button, mLLStickerGroup, false);
                ImageButton imgButton = view.findViewById(R.id.ibIcon);
                TextView textView = view.findViewById(R.id.tvDes);
                Drawable drawable = Drawable.createFromStream(getAssets().open(subGroup.getIcon()), null);
                imgButton.setImageDrawable(drawable);
                imgButton.setTag(subGroup.getFolder());
                textView.setText(subGroup.getTitle());

                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openStickerSubGroupView(v);
                        unSelecteStickerGroupButtonState();
                        view.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                    }
                });
                mLLStickerGroup.addView(view);
            }

        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private void unSelecteStickerGroupButtonState(){
        try {
            for(int i=0; i< mLLStickerGroup.getChildCount(); i++){
                View childView = mLLStickerGroup.getChildAt(i);
                childView.setBackgroundColor(getResources().getColor(R.color.button_default));
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }

    private void showEditSticker() {
        mLlEditSticker.setVisibility(View.VISIBLE);
        Sticker selectedSticker = mStickerView.getHandlingSticker();
        if (selectedSticker != null) {
            BitmapDrawable bitmapD = (BitmapDrawable) selectedSticker.getDrawable();
            if (bitmapD != null) {
                int alpha = bitmapD.getPaint().getAlpha();
                mFakeBar.setProgress(alpha - 100);
            }

        }

    }

    private void hideEditSticker() {
        mStickerView.setHandlingSticker(null);
        mStickerView.invalidate();
        mLlEditSticker.setVisibility(View.GONE);
        hideColorFilter();
    }

    public void openStickerSubGroupView(View view) {
        String folderPath = view.getTag().toString();
        String dataPath = folderPath + "/data.json";
        showStickers(dataPath);
        LogUtils.d(folderPath);
    }

    public void hideGroupSticker() {
        mGridViewSticker.setVisibility(View.GONE);
    }

    public void showColorFilter() {
        mColorListFilterView.setVisibility(View.VISIBLE);
    }

    public void hideColorFilter() {
        mColorListFilterView.setVisibility(View.GONE);
    }

    public void colorFilterClick(View v) {
        if (mColorListFilterView.getVisibility() == View.VISIBLE) {
            View parentView = (View) v.getParent();
            parentView.setBackgroundColor(getResources().getColor(R.color.button_default));
            hideColorFilter();
        } else {
            showColorFilter();
            View parentView = (View) v.getParent();
            parentView.setBackgroundColor(getResources().getColor(R.color.button_pressed));
        }
    }
    public void closeEditStickerClick(View view){
        hideEditSticker();
    }


    private void showStickers(String dataPath) {
        try {
            final List<String> lstStickerPath = JsonUtils.getImagesPathFromJson(this, dataPath);
            StickerAdapter stickerAdapter = new StickerAdapter(this, lstStickerPath);
            mGridViewSticker.setAdapter(stickerAdapter);
            mGridViewSticker.setVisibility(View.VISIBLE);
            mRlHeader.setVisibility(View.GONE);

            mGridViewSticker.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        String path = lstStickerPath.get(i);
                        hideGroupSticker();
                        Drawable d = Drawable.createFromStream(getResources().getAssets().open(path), null);
                        DrawableSticker sticker = new DrawableSticker(d);

                        mStickerView.addSticker(sticker);
                        mRlHeader.setVisibility(View.VISIBLE);
                        unSelecteStickerGroupButtonState();
                        LogUtils.d(path);
                    } catch (Exception exp) {
                        LogUtils.e(exp);
                    }
                }


            });
        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }

    @Override
    public void onBackPressed() {
        if (mGridViewSticker.getVisibility() == View.VISIBLE) {
            hideGroupSticker();
        }
        super.onBackPressed();
    }


    public void resetClick(View view) {
        try {
            hideEditSticker();
            mStickerView.removeAllStickers();;
        }catch (Exception exp){

        }
    }
}
