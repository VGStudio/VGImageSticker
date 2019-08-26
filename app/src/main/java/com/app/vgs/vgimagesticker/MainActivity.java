package com.app.vgs.vgimagesticker;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.vgs.vgimagesticker.utils.ScreenDimension;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class MainActivity extends AppCompatActivity {

    TemplateView template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        ScreenDimension.getScreenSize(this);

        loadNativeAd();
    }

    private void loadNativeAd(){
        int screenWidth = ScreenDimension.mWidth;
        int screenHeight = ScreenDimension.mHeight;



        template = findViewById(R.id.my_template);

        //template.getLayoutParams().height = screenHeight/3;

        template.setVisibility(View.GONE);
        AdLoader.Builder builder = new AdLoader.Builder(
                this, "ca-app-pub-3940256099942544/2247696110");

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                Log.d("VGImageSticker", "Load Native ad full");


                ColorDrawable bgColor = new ColorDrawable(Color.WHITE);
                NativeTemplateStyle styles = new
                        NativeTemplateStyle.Builder().withMainBackgroundColor(bgColor).build();
                template.setVisibility(View.VISIBLE);
                //template.setStyles(styles);
                template.setNativeAd(unifiedNativeAd);
            }
        });

        AdLoader adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }
}
