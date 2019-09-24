package com.app.vgs.vgimagesticker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.vgs.vgimagesticker.Classes.ConnectionDetector;
import com.app.vgs.vgimagesticker.ads.AdUtils;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.ShareUtils;
import com.app.vgs.vgimagesticker.vo.MoreAppGroup;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ShareActivity extends AppCompatActivity {

    public boolean mShowInterstitial = true;

    List<MoreAppGroup> mLstMoreAppGroup;
    ConnectionDetector mCD;
    ImageButton mFacebook, mInstagram, mWallpaper, mShare;

    TemplateView mTemplate;
    public ShareUtils shareUtils;

    public static String imPath= "";
    TextView txtHienThi;
    ImageView imgEditShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_share);

        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mShowInterstitial) {
            shareUtils = new ShareUtils(this);
        }
    }

    private void initData() {
        mLstMoreAppGroup = JsonUtils.getMoreAppSaveJsonData(this, Const.MORE_APP_SAVE_DATA_FILE_PATH);
        addQuangCao();
        readImage();
    }

    private void initView() {
        mCD = new ConnectionDetector(this);
        mFacebook = findViewById(R.id.facebook);
        mInstagram = findViewById(R.id.instagram);
        mWallpaper = findViewById(R.id.wallpaper);
        mShare = findViewById(R.id.multiple);
        mTemplate = findViewById(R.id.my_templateSave);

        txtHienThi = findViewById(R.id.txtHienThi);
        imgEditShare = findViewById(R.id.imgEditPath);
    }



    // thanh quảng cáo ở màn hình
    private void addQuangCao() {
        String admobNativedId = AdUtils.getAdmobNativeId(this);

        mTemplate.setVisibility(View.GONE);
        AdLoader.Builder builder = new AdLoader.Builder(
                this, admobNativedId);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                Log.d("VGImageSticker", "Load Native ad full");
                mTemplate.setVisibility(View.VISIBLE);
                mTemplate.setNativeAd(unifiedNativeAd);
            }
        });
        Log.d("", "");

        AdLoader adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }
    //
    ////////////////////////////////

    //đọc hình ảnh gửi về từ MainAction
    private void readImage(){
        try {
            Intent intent = getIntent();
            imPath = intent.getStringExtra("mImagePath");
            if(imPath == null){
                txtHienThi.setVisibility(View.VISIBLE);
            }
            else {
                Bitmap bitmap = BitmapFactory.decodeFile(imPath);
                imgEditShare.setImageBitmap(bitmap);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void share(View view) {
        if(imPath == null){
            Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
        }
        else {
            shareUtils.shareUtils();
        }
    }

    public void wallpaper(View view) {
        if(imPath == null){
            Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
        }
        else {
            shareUtils.wallpaperUtils();
        }
    }

    // click vào istagram
    public void instagram(View view) {
        if(imPath == null){
            Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
        }
        else {
            shareUtils.checkAppInstall();
            shareUtils.shareInstagram();
        }
    }

    //click vào facebook
    public void facebook(View view) {
        if(imPath == null){
            Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(this, FaceBookActivity.class));
        }
    }
    //
    ///////////////////
}
