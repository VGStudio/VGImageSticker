package com.app.vgs.vgimagesticker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String tittle = "";
    String hinhanh = "";
    String   link = "";
    TemplateView mTemplate;
    ViewFlipper mViewFlipper;
    TextView mTxtTest1,mTxtTest2,mTxtTest3,mTxtTest4,mTxtTest5,mTxtTest6,mTxtTest7,mTxtTest8,mTxtTest9,mTxtTest10,mTxtTest11,mTxtTest12,mTxtTest13,mTxtTest14,mTxtTest15;
    ImageView mImg1,mImg2,mImg3,mImg4,mImg5,mImg6,mImg7,mImg8,mImg9,mImg10,mImg11,mImg12,mImg13,mImg14,mImg15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initData();


    }

    private void initView() {
        mViewFlipper = findViewById(R.id.viewFlipper);
        mTemplate = findViewById(R.id.my_template);
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
        ReadJsonFile();
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

    private void ReadJsonFile(){
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
}
