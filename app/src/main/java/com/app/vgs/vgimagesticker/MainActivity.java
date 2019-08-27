package com.app.vgs.vgimagesticker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

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

public class MainActivity extends AppCompatActivity {

    String tittle = "";
    String hinhanh = "";
    String   link = "";
    int index = 0;
    GridLayout mGrid;
    CardView mCardView;
    TemplateView mTemplate;
    ViewFlipper mViewFlipper;
    TextView mTxtTest1,mTxtTest2,mTxtTest3,mTxtTest4,mTxtTest5,mTxtTest6,mTxtTest7,mTxtTest8,mTxtTest9,mTxtTest10,mTxtTest11,mTxtTest12,mTxtTest13,mTxtTest14,mTxtTest15;
    ImageView mImg1,mImg2,mImg3,mImg4,mImg5,mImg6,mImg7,mImg8,mImg9,mImg10,mImg11,mImg12,mImg13,mImg14,mImg15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initView();
        initData();
    }


    private void initView() {
        mViewFlipper = findViewById(R.id.viewFlipper);
        mTemplate = findViewById(R.id.my_template);
        mGrid = findViewById(R.id.gridMain);
        mTxtTest1 = findViewById(R.id.txtTest1);
        mTxtTest2 = findViewById(R.id.txtTest2);
        mTxtTest3 = findViewById(R.id.txtTest3);
        mTxtTest4 = findViewById(R.id.txtTest4);
        mTxtTest5 = findViewById(R.id.txtTest5);
        mTxtTest6 = findViewById(R.id.txtTest6);
        mTxtTest7 = findViewById(R.id.txtTest7);
        mTxtTest8 = findViewById(R.id.txtTest8);
        mTxtTest9 = findViewById(R.id.txtTest9);
        mTxtTest10 = findViewById(R.id.txtTest10);
        mTxtTest11 = findViewById(R.id.txtTest11);
        mTxtTest12 = findViewById(R.id.txtTest12);
        mTxtTest13 = findViewById(R.id.txtTest13);
        mTxtTest14 = findViewById(R.id.txtTest14);
        mTxtTest15 = findViewById(R.id.txtTest15);
        mImg1      = findViewById(R.id.test1);
        mImg2      = findViewById(R.id.test2);
        mImg3      = findViewById(R.id.test3);
        mImg4      = findViewById(R.id.test4);
        mImg5      = findViewById(R.id.test5);
        mImg6      = findViewById(R.id.test6);
        mImg7      = findViewById(R.id.test7);
        mImg8      = findViewById(R.id.test8);
        mImg9      = findViewById(R.id.test9);
        mImg10      = findViewById(R.id.test10);
        mImg11      = findViewById(R.id.test11);
        mImg12      = findViewById(R.id.test12);
        mImg13      = findViewById(R.id.test13);
        mImg14      = findViewById(R.id.test14);
        mImg15      = findViewById(R.id.test15);

    }

    private void initData() {
        ScreenDimension.getScreenSize(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        int screenWidth = ScreenDimension.mWidth;

        mViewFlipper.getLayoutParams().height = (int) (screenWidth*0.55);

        loadNativeAd();
        readMoreAppJsonFile();
    }

    private void loadNativeAd() {
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
                for(int i=0; i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    hinhanh = jsonObject.getString("icon");
                    tittle  = jsonObject.getString("app_name");
                    link   = jsonObject.getString("link");
                    index   = jsonObject.getInt("index");


                    mCardView = (CardView) mGrid.getChildAt(i);
                    mCardView.setTag(link);
                    // Đọc hình ảnh từ trên mạng
                    URL url = new URL(hinhanh);
                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                    httpConn.connect();
                    InputStream in = httpConn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    //
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

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void clickCardViewMoreApp(){
        mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String linkStore = view.getTag().toString();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(linkStore));
                startActivity(intent);
            }
        });
    }


}
