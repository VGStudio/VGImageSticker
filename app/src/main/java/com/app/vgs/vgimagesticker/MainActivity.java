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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.app.vgs.vgimagesticker.Classes.ConnectionDetector;
import com.app.vgs.vgimagesticker.utils.AdUtils;
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

    WifiManager wifiManager;
    GifImageView mGIV;
    ConnectionDetector mCD;
    String tittle = "";
    String hinhanh = "";
    String   link = "";
    int i = 0;
    Bitmap bitmap = null;
    GridLayout mGrid;
    CardView mCardView;
    TemplateView mTemplate;
    ViewFlipper mViewFlipper;
    TextView mTxtTest1,mTxtTest2,mTxtTest3,mTxtTest4,mTxtTest5,mTxtTest6,mTxtTest7,mTxtTest8,mTxtTest9,mTxtTest10,mTxtTest11,mTxtTest12,mTxtTest13,mTxtTest14,mTxtTest15;
    ImageView mImg1,mImg2,mImg3,mImg4,mImg5,mImg6,mImg7,mImg8,mImg9,mImg10,mImg11,mImg12,mImg13,mImg14,mImg15;
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
        mGrid           = findViewById(R.id.gridMain);
        mTxtTest1       = findViewById(R.id.txtTest1);
        mTxtTest2       = findViewById(R.id.txtTest2);
        mTxtTest3       = findViewById(R.id.txtTest3);
        mTxtTest4       = findViewById(R.id.txtTest4);
        mTxtTest5       = findViewById(R.id.txtTest5);
        mTxtTest6       = findViewById(R.id.txtTest6);
        mTxtTest7       = findViewById(R.id.txtTest7);
        mTxtTest8       = findViewById(R.id.txtTest8);
        mTxtTest9       = findViewById(R.id.txtTest9);
        mTxtTest10      = findViewById(R.id.txtTest10);
        mTxtTest11      = findViewById(R.id.txtTest11);
        mTxtTest12      = findViewById(R.id.txtTest12);
        mTxtTest13      = findViewById(R.id.txtTest13);
        mTxtTest14      = findViewById(R.id.txtTest14);
        mTxtTest15      = findViewById(R.id.txtTest15);
        mImg1           = findViewById(R.id.test1);
        mImg2           = findViewById(R.id.test2);
        mImg3           = findViewById(R.id.test3);
        mImg4           = findViewById(R.id.test4);
        mImg5           = findViewById(R.id.test5);
        mImg6           = findViewById(R.id.test6);
        mImg7           = findViewById(R.id.test7);
        mImg8           = findViewById(R.id.test8);
        mImg9           = findViewById(R.id.test9);
        mImg10          = findViewById(R.id.test10);
        mImg11          = findViewById(R.id.test11);
        mImg12          = findViewById(R.id.test12);
        mImg13          = findViewById(R.id.test13);
        mImg14          = findViewById(R.id.test14);
        mImg15          = findViewById(R.id.test15);
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

    // tạo chức năng khi click vào các CardView ở MoreApp
    private void clickCardViewMoreApp(){
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCD.isConected()) {
                    Intent intent = new Intent();
                    String linkStore = view.getTag().toString();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(linkStore));
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "Bạn Phải Kết Nối Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
        mCardView = (CardView) mGrid.getChildAt(i);
        mCardView.setTag(link);
        readDataImageUrl();
        if (i == 0 ) {
            mTxtTest1.setText(tittle);
            this.mImg1.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest1.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 1 ) {
            mTxtTest2.setText(tittle);
            this.mImg2.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest2.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 2  ) {
            // if(!hinhanh.equals("") && !tittle.equals("") && !link.equals("")) {
            mTxtTest3.setText(tittle);
            this.mImg3.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest3.equals("") ){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 3 ) {
            mTxtTest4.setText(tittle);
            this.mImg4.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest4.equals("") ){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 4) {
            mTxtTest5.setText(tittle);
            this.mImg5.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest5.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 5) {
            mTxtTest6.setText(tittle);
            this.mImg6.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest6.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 6) {
            mTxtTest7.setText(tittle);
            this.mImg7.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest7.equals("") ){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 7 ) {
            mTxtTest8.setText(tittle);
            this.mImg8.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest8.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 8) {
            mTxtTest9.setText(tittle);
            this.mImg9.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest9.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 9) {
            mTxtTest10.setText(tittle);
            this.mImg10.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest10.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 10) {
            mTxtTest11.setText(tittle);
            this.mImg11.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest11.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 11) {
            mTxtTest12.setText(tittle);
            this.mImg12.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest12.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 12) {
            mTxtTest13.setText(tittle);
            this.mImg13.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest13.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 13) {
            mTxtTest14.setText(tittle);
            this.mImg14.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest14.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        } else if (i == 14) {
            mTxtTest15.setText(tittle);
            this.mImg15.setImageBitmap(bitmap);
            clickCardViewMoreApp();
            if(!mTxtTest15.equals("")){
                mCardView.setVisibility(View.VISIBLE);
            }
        }
    }


    public void openGallery(View view) {
        pickGallery();
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
}
