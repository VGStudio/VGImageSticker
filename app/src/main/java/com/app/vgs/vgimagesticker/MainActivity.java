package com.app.vgs.vgimagesticker;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.vgs.vgimagesticker.Classes.ConnectionDetector;
import com.app.vgs.vgimagesticker.ads.AdUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.ScreenDimension;
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

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {

    public static final int REQUEST_IMAGE_FROM_GALLERY = 10001;
    public static final int REQUEST_IMAGE_FROM_CAMERA = 10002;


    WifiManager wifiManager;
    GifImageView mGIV;
    ConnectionDetector mCD;
    String tittle = "";
    String hinhanh = "";
    String   link = "";
    int i = 0;
    Bitmap bitmap = null;
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
    }

    // Ánh Xạ
    private void initView() {
        wifiManager     = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mCD             = new ConnectionDetector(this);
        mViewFlipper    = findViewById(R.id.viewFlipper);
        mTemplate       = findViewById(R.id.my_template);


        mImgCamera      = findViewById(R.id.imgCamera);
        mImgGallery     = findViewById(R.id.imgGallery);
        mGIV            = findViewById(R.id.gifMoreApp);
    }

    private void initData() {

        ScreenDimension.getScreenSize(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        int screenWidth = ScreenDimension.mWidth;

        mViewFlipper.getLayoutParams().height = (int) (screenWidth*0.55);

        loadNativeAd();
        if(mCD.isConected()){
            readMoreAppJsonFile();
        }else {
            mGIV.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Vui Lòng Bật Kết Nối!", Toast.LENGTH_SHORT).show();
        }
//        if (mGIV.getVisibility() == View.VISIBLE  && wifiManager.setWifiEnabled(true)){
//            mGIV.setVisibility(View.GONE);
//            readMoreAppJsonFile();
//        }
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

    // Đọc dữ liệu file json
    private void readMoreAppJsonFile(){
        String json = "imagemain.json";
        StringBuilder stringBuilder = new StringBuilder("");
        BufferedReader reader = null;
        String rtn = "";
        int index =0;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(json)));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                stringBuilder.append(mLine);
            }
            rtn = stringBuilder.toString();
            try {
                JSONObject jsonObject = new JSONObject(rtn);
                JSONArray jsonArray = jsonObject.getJSONArray("apps");
                for( i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    hinhanh = jsonObject.getString("icon");
                    tittle  = jsonObject.getString("app_name");
                    link   = jsonObject.getString("link");
                    index   = jsonObject.getInt("index");
                    fillDataJsonMoreApp();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }



    // Đọc hình ảnh từ trên mạng
    private void readDataImageUrl(){
        try {
            URL url = new URL(hinhanh);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.connect();
            InputStream in = httpConn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // đổ dữ liệu nhận được từ Json ra màn hình MoreApp
    private void fillDataJsonMoreApp(){
        readDataImageUrl();

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = false;
    }

    @Override
    public void closeInterstitial() {
    }



    //event listener
    public void openMyImagesClick(View view){

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

            startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), REQUEST_IMAGE_FROM_GALLERY);
        }
    }


    public void openGallery(View view) {
        pickGallery();
    }

    public void openCameraClick(View view){
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermission(Manifest.permission.CAMERA,
                        getString(R.string.permission_capture_image),
                        REQUEST_IMAGE_FROM_CAMERA);
            } else {
                Toast.makeText(this, "Cammera click", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_FROM_CAMERA);
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }
    }

}
