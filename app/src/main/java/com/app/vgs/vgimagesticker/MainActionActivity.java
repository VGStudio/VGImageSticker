package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class MainActionActivity extends BaseActivity {

    public static final String KEY_IMAGE_PATH_UPDATE = "KEY_IMAGE_PATH_UPDATE";
    public static final int EDIT_IMAGE_CODE = 1001;

    View mExitPopUp;
    private AdView mBannerAdView;
    private ImageButton mIbStickerGroup1;
    private TextView mTvStickerGroup1;
    private ImageButton mIbStickerGroup2;
    private TextView mTvStickerGroup2;
    private ImageView mIvPreview;


    List<StickerGroup> mLstStickerGroup;
    StickerGroup mStickerGroup1;
    StickerGroup mStickerGroup2;

    private String mImagePath = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);
        initView();
        initData();

        //mIvPreview.setImageResource(R.drawable.gai_xinh3);
        Drawable d = getResources().getDrawable(R.drawable.gai_xinh3);
        mIvPreview.setImageDrawable(d);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == EDIT_IMAGE_CODE && resultCode == Activity.RESULT_OK){
            mImagePath = data.getStringExtra(KEY_IMAGE_PATH_UPDATE);
            Drawable dPreview = Drawable.createFromPath(mImagePath);
            mIvPreview.setImageDrawable(dPreview);
        }
    }

    private void initView() {
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIbStickerGroup1 = findViewById(R.id.ibStickerGroup1);
        mTvStickerGroup1 = findViewById(R.id.tvStickerGroup1);
        mIbStickerGroup2 = findViewById(R.id.ibStickerGroup2);
        mTvStickerGroup2 = findViewById(R.id.tvStickerGroup2);
        mIvPreview = findViewById(R.id.ivPreview);



        mExitPopUp.setVisibility(View.GONE);
    }

    private void initData() {
        try {
            initAds();
            fillDataForStickerGroup();

        }catch (Exception exp){
            LogUtils.e(exp);
        }


    }

    private void fillDataForStickerGroup(){
        try {
            mLstStickerGroup = JsonUtils.getStickerGroupFromJsonData(this, Const.STICKER_DATA_FILE_PATH);
            mStickerGroup1 = mLstStickerGroup.get(0);
            mStickerGroup2 = mLstStickerGroup.get(1);

            mTvStickerGroup1.setText(mStickerGroup1.getTitle());
            mTvStickerGroup2.setText(mStickerGroup2.getTitle());

            Drawable d1 = Drawable.createFromStream(getResources().getAssets().open(mStickerGroup1.getIcon()), null);
            Drawable d2 = Drawable.createFromStream(getResources().getAssets().open(mStickerGroup2.getIcon()), null);
            mIbStickerGroup1.setImageDrawable(d1);
            mIbStickerGroup2.setImageDrawable(d2);
        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }


    public void stickerGroup1Click(View view){
        openStickerActivity(mStickerGroup1.getId());
    }
    public void stickerGroup2Click(View view){
        openStickerActivity(mStickerGroup2.getId());
    }

    private void openStickerActivity(String groupId){
        Intent intent = new Intent(this, StickerActivity.class);
        intent.putExtra(StickerActivity.KEY_GROUP_STICKER_ID, groupId);
        startActivityForResult(intent, EDIT_IMAGE_CODE);
    }


    @Override
    public void setShowInterstitial() {
        mShowInterstitial = true;
    }

    private void initAds(){
        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.loadAd(adRequest);
    }

    public void noExitClick(View view){
        hideExitPop();
    }
    public void yesExitClick(View view){
        if(!showInterstitial()){
            finish();
        }
    }

    public void backClick(View view){
        exitScreen(true);
    }
    private void showExitPop(){
        mExitPopUp.setVisibility(View.VISIBLE);
    }
    private void hideExitPop(){
        mExitPopUp.setVisibility(View.GONE);
    }

    public void exitScreen(boolean confirmExit){
        if(confirmExit){
            showExitPop();
        }else{
            finish();
        }
    }

    public void closeInterstitial() {
        finish();
    }

    @Override
    public void onBackPressed() {
        showExitPop();
    }
}
