package com.app.vgs.vgimagesticker;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

public class MainActionActivity extends BaseActivity {

    public static final int STICKER_ACTIVITY_CODE = 1001;

    View mExitPopUp;
    private AdView mBannerAdView;
    private ImageButton mIbStickerGroup1;
    private TextView mTvStickerGroup1;
    private ImageButton mIbStickerGroup2;
    private TextView mTvStickerGroup2;


    List<StickerGroup> mLstStickerGroup;
    StickerGroup mStickerGroup1;
    StickerGroup mStickerGroup2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);
        initView();
        initData();
    }

    private void initView() {
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIbStickerGroup1 = findViewById(R.id.ibStickerGroup1);
        mTvStickerGroup1 = findViewById(R.id.tvStickerGroup1);
        mIbStickerGroup2 = findViewById(R.id.ibStickerGroup2);
        mTvStickerGroup2 = findViewById(R.id.tvStickerGroup2);



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
        startActivityForResult(intent, STICKER_ACTIVITY_CODE);
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
