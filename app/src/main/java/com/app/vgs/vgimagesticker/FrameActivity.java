package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.adapter.FrameAdapter;
import com.app.vgs.vgimagesticker.adapter.StickerAdapter;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.app.vgs.vgimagesticker.vo.FrameGroup;
import com.app.vgs.vgimagesticker.vo.FrameSubGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.xiaopo.flying.sticker.DrawableSticker;

import java.io.File;
import java.util.List;

public class FrameActivity extends BaseActivity {
    LinearLayout mLLFrameGroup;
    List<FrameGroup> mLstFrameGroup;
    public static String KEY_FRAME_GROUP_ID = "KEY_FRAME_GROUP_ID";

    private String mFrameGroupId = "";
    private String mFileSavedpath = null;

    GridView mGridViewFrame;
    RelativeLayout mRlHeader;
    ImageView mIvPreview;

    View mExitPopUp;
    AdView mBannerAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);

        initView();
        initData();

        addFrameGroupIconView();
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
        mFrameGroupId = getIntent().getStringExtra(KEY_FRAME_GROUP_ID);
        mLstFrameGroup = JsonUtils.getFrameGroupFromJsonData(this, "frames/data.json");
        LogUtils.d(mLstFrameGroup.size()+"");
    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void initView() {
        mLLFrameGroup = findViewById(R.id.llFrameGroup);
        mGridViewFrame = findViewById(R.id.gridViewFrame);
        mRlHeader = findViewById(R.id.rlHeader);
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIvPreview = findViewById(R.id.ivPreview);





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


    private void addFrameGroupIconView() {
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            FrameGroup frame = null;

            for (FrameGroup sframeGroup : mLstFrameGroup) {
                if (sframeGroup.getId().equals(mFrameGroupId)) {
                    frame = sframeGroup;
                }
            }

            mLLFrameGroup.setWeightSum(frame.getLstSubGroup().size());


            for (FrameSubGroup subGroup : frame.getLstSubGroup()) {
                final View view = layoutInflater.inflate(R.layout.layout_sub_button, mLLFrameGroup, false);
                ImageButton imgButton = view.findViewById(R.id.ibIcon);
                TextView textView = view.findViewById(R.id.tvDes);
                Drawable drawable = Drawable.createFromStream(getAssets().open(subGroup.getIcon()), null);
                imgButton.setImageDrawable(drawable);
                imgButton.setTag(subGroup.getFolder());
                textView.setText(subGroup.getTitle());

                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFrameSubGroupView(v);
                        unSelecteFrameGroupButtonState();
                        view.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                    }
                });
                mLLFrameGroup.addView(view);
            }

        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private void unSelecteFrameGroupButtonState() {
        try {
            for (int i = 0; i < mLLFrameGroup.getChildCount(); i++) {
                View childView = mLLFrameGroup.getChildAt(i);
                childView.setBackgroundColor(getResources().getColor(R.color.button_default));
            }
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }


    public void openFrameSubGroupView(View view) {
        String folderPath = view.getTag().toString();
        String dataPath = folderPath + "/data.json";
        showFrames(dataPath);
        LogUtils.d(folderPath);
    }

    public void hideFrameGroup() {
        mGridViewFrame.setVisibility(View.GONE);
        unSelecteFrameGroupButtonState();
        mRlHeader.setVisibility(View.VISIBLE);
    }


    private void showExitPopUp() {
        mExitPopUp.setVisibility(View.VISIBLE);
    }

    private void hideExitPopUp() {
        mExitPopUp.setVisibility(View.GONE);
    }


    private void showFrames(String dataPath) {
        try {
            final List<String> lstFramePath = JsonUtils.getImagesPathFromJson(this, dataPath);
            FrameAdapter frameAdapter = new FrameAdapter(this, lstFramePath);
            mGridViewFrame.setAdapter(frameAdapter);
            mGridViewFrame.setVisibility(View.VISIBLE);
            mRlHeader.setVisibility(View.GONE);

            mGridViewFrame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        String path = lstFramePath.get(i);
                        hideFrameGroup();
                        Drawable d = Drawable.createFromStream(getResources().getAssets().open(path), null);
                        DrawableSticker sticker = new DrawableSticker(d);


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

    public void saveImageClick(View view) {
        SaveFileTask task = new SaveFileTask(this);
        task.execute();
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
        if (mGridViewFrame.getVisibility() == View.VISIBLE) {
            hideFrameGroup();
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
