package com.app.vgs.vgimagesticker;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.xiaopo.flying.sticker.StickerIconEvent;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.ZoomIconEvent;

import java.util.Arrays;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    LinearLayout mLLStickerGroup;
    List<StickerGroup> mLstStickerGroup;
    public static String KEY_GROUP_STICKER_ID = "KEY_GROUP_STICKER_ID";

    private String mStickerId = "";

    GridView mGridViewSticker;
    RelativeLayout mRlHeader;
    StickerView mStickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        initView();
        initData();

        addView();
    }

    private void initData() {
        mLstStickerGroup = JsonUtils.getStickerGroupFromJsonData(this, "sticker/data.json");
        mStickerId = getIntent().getStringExtra(KEY_GROUP_STICKER_ID);
    }

    private void initView() {
        mLLStickerGroup = findViewById(R.id.llStickerGroup);
        mGridViewSticker = findViewById(R.id.gridViewSticker);
        mRlHeader = findViewById(R.id.rlHeader);
        mStickerView = findViewById(R.id.sticker_view);

        initStickerView();
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

                }

                @Override
                public void onStickerClicked(@NonNull Sticker sticker) {
                    LogUtils.d("Sticker click");
                }

                @Override
                public void onStickerDeleted(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerDragFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerTouchedDown(@NonNull Sticker sticker) {
                    LogUtils.d("Sticker TouchedDown");
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

    private void addView() {
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
                View view = layoutInflater.inflate(R.layout.layout_sub_button, mLLStickerGroup, false);
                ImageButton imgButton = view.findViewById(R.id.ibIcon);
                TextView textView = view.findViewById(R.id.tvDes);
                Drawable drawable = Drawable.createFromStream(getAssets().open(subGroup.getIcon()), null);
                imgButton.setImageDrawable(drawable);
                imgButton.setTag(subGroup.getFolder());
                textView.setText(subGroup.getTitle());

                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openStickerSubGroupView(view);
                    }
                });
                mLLStickerGroup.addView(view);
            }

        } catch (Exception exp) {
            LogUtils.e(exp);
        }
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



}
