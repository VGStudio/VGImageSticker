package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.FrameGroup;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.net.URI;
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
    private ImageButton mIbStickerGroup3;
    private TextView mTvStickerGroup3;

    private ImageButton mIbFrameGroup1;
    private TextView mTvFrameGroup1;
    private ImageButton mIbFrameGroup2;
    private TextView mTvFrameGroup2;



    private ImageView mIvPreview;


    List<StickerGroup> mLstStickerGroup;
    StickerGroup mStickerGroup1;
    StickerGroup mStickerGroup2;
    StickerGroup mStickerGroup3;


    List<FrameGroup> mLstFrameGroup;
    FrameGroup mFrameGroup1;
    FrameGroup mFrameGroup2;


    private String mImagePath = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action);
        initView();
        initData();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            if(requestCode == EDIT_IMAGE_CODE && resultCode == Activity.RESULT_OK){
                if(data.getExtras().containsKey(KEY_IMAGE_PATH_UPDATE)){
                    mImagePath = data.getStringExtra(KEY_IMAGE_PATH_UPDATE);
                    Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);

                    mIvPreview.setImageBitmap(bitmap);
                }
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }

    private void initView() {
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIbStickerGroup1 = findViewById(R.id.ibStickerGroup1);
        mTvStickerGroup1 = findViewById(R.id.tvStickerGroup1);
        mIbStickerGroup2 = findViewById(R.id.ibStickerGroup2);
        mTvStickerGroup2 = findViewById(R.id.tvStickerGroup2);
        mIbStickerGroup3 = findViewById(R.id.ibStickerGroup3);
        mTvStickerGroup3 = findViewById(R.id.tvStickerGroup3);

        mIbFrameGroup1 = findViewById(R.id.ibFrameGroup1);
        mTvFrameGroup1 = findViewById(R.id.tvFrameGroup1);
        mIbFrameGroup2 = findViewById(R.id.ibFrameGroup2);
        mTvFrameGroup2 = findViewById(R.id.tvFrameGroup2);

        mIvPreview = findViewById(R.id.ivPreview);



        mExitPopUp.setVisibility(View.GONE);
    }

    private void initData() {
        try {
            initAds();
            fillDataForStickerGroup();
            fillDataForFrameGroup();

        }catch (Exception exp){
            LogUtils.e(exp);
        }


    }

    private void fillDataForStickerGroup(){
        try {
            mLstStickerGroup = JsonUtils.getStickerGroupFromJsonData(this, Const.STICKER_DATA_FILE_PATH);
            mStickerGroup1 = mLstStickerGroup.get(0);
            mStickerGroup2 = mLstStickerGroup.get(1);
            mStickerGroup3 = mLstStickerGroup.get(2);

            mTvStickerGroup1.setText(mStickerGroup1.getTitle());
            mTvStickerGroup2.setText(mStickerGroup2.getTitle());
            mTvStickerGroup3.setText(mStickerGroup3.getTitle());

            Bitmap bitmap1 = BitmapFactory.decodeStream(getResources().getAssets().open(mStickerGroup1.getIcon()));
            Bitmap bitmap2 = BitmapFactory.decodeStream(getResources().getAssets().open(mStickerGroup2.getIcon()));
            Bitmap bitmap3 = BitmapFactory.decodeStream(getResources().getAssets().open(mStickerGroup3.getIcon()));

            mIbStickerGroup1.setImageBitmap(bitmap1);
            mIbStickerGroup2.setImageBitmap(bitmap2);
            mIbStickerGroup3.setImageBitmap(bitmap3);

        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }

    private void fillDataForFrameGroup(){
        try {
            mLstFrameGroup = JsonUtils.getFrameGroupFromJsonData(this, Const.FRAME_DATA_FILE_PATH);
            mFrameGroup1 = mLstFrameGroup.get(0);
            mFrameGroup2 = mLstFrameGroup.get(1);

            mTvFrameGroup1.setText(mFrameGroup1.getTitle());
            mTvFrameGroup2.setText(mFrameGroup2.getTitle());

            Bitmap bitmap1 = BitmapFactory.decodeStream(getAssets().open(mFrameGroup1.getIcon()));
            mIbFrameGroup1.setImageBitmap(bitmap1);

            Bitmap bitmap2 = BitmapFactory.decodeStream(getAssets().open(mFrameGroup2.getIcon()));
            mIbFrameGroup2.setImageBitmap(bitmap2);

        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }


    public void openStickerActivityClick(View view){
        openStickerActivity();
    }
    public void stickerGroup1Click(View view){
        openStickerActivity(mStickerGroup1.getId());
    }
    public void stickerGroup2Click(View view){
        openStickerActivity(mStickerGroup2.getId());
    }
    public void stickerGroup3Click(View view){
        openStickerActivity(mStickerGroup3.getId());
    }

    public void frameGroup1Click(View view){
        openFrameActivity(mFrameGroup1.getId());
    }
    public void frameGroup2Click(View view){
        openFrameActivity(mFrameGroup2.getId());
    }

    private void openStickerActivity(String groupId){
        Intent intent = new Intent(this, StickerActivity.class);
        intent.putExtra(StickerActivity.KEY_GROUP_STICKER_ID, groupId);
        startActivityForResult(intent, EDIT_IMAGE_CODE);
    }

    private void openFrameActivity(String groupId){
        Intent intent = new Intent(this, FrameActivity.class);
        intent.putExtra(FrameActivity.KEY_FRAME_GROUP_ID, groupId);
        startActivityForResult(intent, EDIT_IMAGE_CODE);
    }

    private void openStickerActivity(){
        Intent intent = new Intent(this, EffectActivity.class);
        //intent.putExtra(FrameActivity.KEY_FRAME_GROUP_ID, groupId);
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
