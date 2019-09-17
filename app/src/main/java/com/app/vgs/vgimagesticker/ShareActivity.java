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
    GridLayout mGrid;
    ConnectionDetector mCD;
    GifImageView mGIV;
    ImageButton mFacebook, mInstagram, mWallpaper, mShare;

    TemplateView mTemplate;
    public ShareUtils shareUtils;
    String imgUrl;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_share);
        supportImage();

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
        addMoreAppIconView();
    }

    private void initView() {
        mGIV = findViewById(R.id.gifMoreAppSave);
        mGrid = findViewById(R.id.gridSave);
        mCD = new ConnectionDetector(this);
        mFacebook = findViewById(R.id.facebook);
        mInstagram = findViewById(R.id.instagram);
        mWallpaper = findViewById(R.id.wallpaper);
        mShare = findViewById(R.id.multiple);
        mTemplate = findViewById(R.id.my_templateSave);
    }

    // add dữ liệu từ json vào moreapp
    private void addMoreAppIconView() {
        try {

            mGrid.setRowCount(mLstMoreAppGroup.size());
            if (mCD.isConected() == true) {
                fillDataMoreApp();
            } else {
                mGIV.setVisibility(View.VISIBLE);
            }
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }


    // đưa ra dữ liệu của json
    private void fillDataMoreApp() {
        LayoutInflater layoutInflater = getLayoutInflater();
        for (final MoreAppGroup moreAppGroup1 : mLstMoreAppGroup) {
            final View view = layoutInflater.inflate(R.layout.layout_sub_btn_moreapp, mGrid, false);
            ImageView imgButton = view.findViewById(R.id.imgMoreApp);
            TextView textView = view.findViewById(R.id.txtMoreApp);
            textView.setText(moreAppGroup1.getTittle());
            imgUrl = moreAppGroup1.getIcon();
            readDataImageInternet();
            imgButton.setImageBitmap(bitmap);

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCD.isConected() == true) {
                        String link = moreAppGroup1.getLink();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(link));
                        startActivity(intent);
                    } else {
                        Toast.makeText(ShareActivity.this, "Are you connected to the internet?", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mGrid.addView(view);
        }
    }

    // đọc dữ liệu hình ảnh trên mạng
    public void readDataImageInternet() {
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            InputStream in = httpConn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //support fill data Image for Internet
    public void supportImage() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    /////////////////////

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

    public void share(View view) {
        shareUtils.shareUtils();
    }

    public void wallpaper(View view) {
        shareUtils.wallpaperUtils();
    }

    // click vào istagram
    public void instagram(View view) {
        shareUtils.checkAppInstall();
        shareUtils.shareInstagram();
    }

    //click vào facebook
    public void facebook(View view) {
        startActivity(new Intent(this,FaceBookActivity.class));
    }
    //
    ///////////////////
}
