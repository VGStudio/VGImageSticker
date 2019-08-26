package com.app.vgs.vgimagesticker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ViewFlipper;

import com.app.vgs.vgimagesticker.utils.AdUtils;
import com.app.vgs.vgimagesticker.utils.ScreenDimension;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class MainActivity extends AppCompatActivity {

    TemplateView mTemplate;
    ViewFlipper mViewFlipper;

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

    }

    private void initData() {
        ScreenDimension.getScreenSize(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        int screenWidth = ScreenDimension.mWidth;

        mViewFlipper.getLayoutParams().height = (int) (screenWidth*0.55);

        loadNativeAd();
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
}
