package com.app.vgs.vgimagesticker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.vgs.vgimagesticker.Classes.ConnectionDetector;
import com.app.vgs.vgimagesticker.ads.AdUtils;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.ScreenDimension;
import com.app.vgs.vgimagesticker.vo.MoreAppGroup;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {

    public static final int REQUEST_IMAGE_FROM_GALLERY = 10001;
    public static final int REQUEST_IMAGE_FROM_CAMERA = 10002;

    List<MoreAppGroup> mLstMoreAppGroup;




    WifiManager wifiManager;
    GifImageView mGIV;
    ConnectionDetector mCD;
    int i = 0;
    GridLayout mGrid;
    TemplateView mTemplate;
    ViewFlipper mViewFlipper;
    ImageButton mImgCamera,mImgGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initView();
        initData();

        addMoreAppIconView();
    }

    // Ánh Xạ
    private void initView() {
        wifiManager     = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mCD             = new ConnectionDetector(this);
        mViewFlipper    = findViewById(R.id.viewFlipper);
        mTemplate       = findViewById(R.id.my_template);
        mGrid           = findViewById(R.id.gridMain);
        mImgCamera      = findViewById(R.id.imgCamera);
        mImgGallery     = findViewById(R.id.imgGallery);
        mGIV            = findViewById(R.id.gifMoreApp);
    }

    private void initData() {

        mLstMoreAppGroup = JsonUtils.getMoreAppJsonData(this, Const.MORE_APP_DATA_FILE_PATH);
        ScreenDimension.getScreenSize(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        int screenWidth = ScreenDimension.mWidth;

        mViewFlipper.getLayoutParams().height = (int) (screenWidth*0.55);
        loadNativeAd();

    }

    // add dữ liệu từ json vào moreapp
    private void addMoreAppIconView() {
        try {

            mGrid.setRowCount(mLstMoreAppGroup.size());

            if(mCD.isConected() == true) {
                fillDataMoreApp();
            }
            else{
                mGIV.setVisibility(View.VISIBLE);
            }
            }
            catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    //
    private void fillDataMoreApp(){
        LayoutInflater layoutInflater = getLayoutInflater();
        Bitmap bitmap =null;

        for (final MoreAppGroup moreAppGroup1 : mLstMoreAppGroup) {
            final View view = layoutInflater.inflate(R.layout.layout_sub_btn_moreapp, mGrid, false);
            ImageView imgButton = view.findViewById(R.id.imgMoreApp);
            TextView textView = view.findViewById(R.id.txtMoreApp);
            textView.setText(moreAppGroup1.getTittle());
            String imgUrl = moreAppGroup1.getIcon();
            try {
                URL url = new URL(imgUrl);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.connect();
                InputStream in = httpConn.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            imgButton.setImageBitmap(bitmap);

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCD.isConected() == true) {
                        String link = moreAppGroup1.getLink();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(link));
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Are you connected to the internet?", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mGrid.addView(view);
        }
    }

    // chạy quảng cáo
    private void loadNativeAd() {
        //Đọc chuỗi các địa chỉ web quảng cáo
        //AdUntils là các kiểu cho quảng cáo
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
                    mViewFlipper.showNext();
            }
        });
        Log.d("","");

        AdLoader adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void openCamera(View view){
        pickCamera();
    }

    public void pickCamera(){
        try {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},REQUEST_IMAGE_FROM_CAMERA);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //
//////////////////////
    public void openGallery(View view) {
        pickGallery();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_IMAGE_FROM_GALLERY) {
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        //startCrop(selectedUri);
                        openCropImageActivity(selectedUri);
                    } else {
                        Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_FROM_CAMERA) {
                if (resultCode == RESULT_OK) {
                    final Uri selectedUri = data.getData();
                    if (selectedUri != null) {
                        //startCrop(selectedUri);
                        openCropImageActivity(selectedUri);
                    } else {
                        Toast.makeText(this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openCropImageActivity(@NonNull  Uri imageSelectedUri){
        Intent intent = new Intent(this, CropImageActivity.class);
        intent.putExtra(CropImageActivity.IMAGE_SELECTED_URI, imageSelectedUri.toString());
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_STORAGE_READ_ACCESS_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickGallery();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        try {
            if (requestCode == REQUEST_IMAGE_FROM_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_FROM_CAMERA);
            } else {
                Toast.makeText(this, "Bạn Không Cho Phép Mở Camera", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pickGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                    .setType("image/*")
                    .addCategory(Intent.CATEGORY_OPENABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }

            startActivityForResult(Intent.createChooser(intent, "Select picture"), REQUEST_IMAGE_FROM_GALLERY);
        }
    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = false;
    }

    @Override
    public void closeInterstitial() {
    }
}
