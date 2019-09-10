package com.app.vgs.vgimagesticker;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.WallpaperManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
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
import com.app.vgs.vgimagesticker.vo.MoreAppGroup;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class ShareActivity extends AppCompatActivity {

    List<MoreAppGroup> mLstMoreAppGroup;
    GridLayout mGrid;
    ConnectionDetector mCD;
    GifImageView mGIV;
    ImageButton mFacebook,mInstagram,mWallpaper,mShare;

    File mFileImagePath = new File(" /Downloads/hong.jpg  ");  // Just example you use file URL
    TemplateView mTemplate;



    WallpaperManager wallpaperManager;
    DisplayMetrics displayMetrics;
    int width,height;
    Bitmap bitmap1,bitmap2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_share);
        StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        initView();
        initData();
    }

    private void initData() {
        mLstMoreAppGroup = JsonUtils.getMoreAppSaveJsonData(this, Const.MORE_APP_SAVE_DATA_FILE_PATH);
        facebook();
        instagram();
        wallpaper();
        share();
        addQuangCao();
        addMoreAppIconView();
    }

    private void initView() {
        mGIV            = findViewById(R.id.gifMoreAppSave);
        mGrid           = findViewById(R.id.gridSave);
        mCD             = new ConnectionDetector(this);
        mFacebook       = findViewById(R.id.facebook);
        mInstagram      = findViewById(R.id.instagram);
        mWallpaper      = findViewById(R.id.wallpaper);
        mShare          = findViewById(R.id.multiple);
        mTemplate       = findViewById(R.id.my_templateSave);
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

    // đưa ra dữ liệu của json
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
                        Toast.makeText(ShareActivity.this, "Are you connected to the internet?", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            mGrid.addView(view);
        }
    }
    //

    // thanh quảng cáo ở màn hình
    private void addQuangCao(){
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
        Log.d("","");

        AdLoader adLoader = builder.build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }

    //click vào facebook
    @SuppressLint("WrongConstant")
    public void facebook(){
        mFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isApplicationSentToBackground(getApplicationContext()))
                {
                    Intent paramView;
                    paramView = new Intent("android.intent.action.SEND");
                    paramView.setType("text/plain");
                    paramView.putExtra("android.intent.extra.TITLE", "Smarty App");
                    paramView.putExtra("android.intent.extra.TEXT", "Created using Smarty App. Use following install link to download :- https://play.google.com/store/apps/details?id=com.appwallet.smarty");
                    paramView.putExtra("android.intent.extra.SUBJECT", "Smarty App");
                    Object localObject1 = new StrictMode.VmPolicy.Builder();
                    StrictMode.setVmPolicy(((StrictMode.VmPolicy.Builder)localObject1).build());
                    if (Build.VERSION.SDK_INT >= 18) {
                        ((StrictMode.VmPolicy.Builder)localObject1).detectFileUriExposure();
                    }
                    try {
                        localObject1 = Uri.parse(getIntent().getStringExtra("imageToShare-uri"));
                        paramView.setType("image/png");
                        paramView.putExtra("android.intent.extra.STREAM", (Parcelable) localObject1);
                        localObject1 = getApplicationContext().getPackageManager().queryIntentActivities(paramView, 0).iterator();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    while (((Iterator)localObject1).hasNext())
                    {
                        Object localObject2 = (ResolveInfo)((Iterator)localObject1).next();
                        if (((ResolveInfo)localObject2).activityInfo.name.contains("facebook"))
                        {
                            localObject2 = ((ResolveInfo)localObject2).activityInfo;
                            localObject2 = new ComponentName(((ActivityInfo)localObject2).applicationInfo.packageName, ((ActivityInfo)localObject2).name);
                            paramView.addCategory("android.intent.category.LAUNCHER");
                            paramView.setFlags(270532608);
                            paramView.setComponent((ComponentName)localObject2);
                            startActivity(paramView);
                        }
                    }
                }
            }
        });


    }
    //hỗ trợ facebook
    public boolean isApplicationSentToBackground(Context paramContext)
    {
        @SuppressLint("WrongConstant") List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1);
        return (!localList.isEmpty()) && (!((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getPackageName().equals(paramContext.getPackageName()));
    }
    //

    // click vào istagram
    private void instagram(){
        mInstagram.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                shareInstagram(mFileImagePath);
            }
        });
    }

    // click vào wall paper
    private void wallpaper(){
        mWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetScreenWidthHeight();
                SetBitmapSize();
                wallpaperManager = WallpaperManager.getInstance(ShareActivity.this);
                try {
                    wallpaperManager.setBitmap(bitmap2);
                    wallpaperManager.suggestDesiredDimensions(width, height);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // click  vào share
    private void share(){
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);

                // If you want to share a png image only, you can do:
                // setType("image/png"); OR for jpeg: setType("image/jpeg");
                share.setType("image/*");

                // Make sure you put example png image named myImage.png in your
                // directory
                String imagePath = Environment.getExternalStorageDirectory()
                        + "/myImage.png";

                File imageFileToShare = new File(imagePath);

                Uri uri = Uri.fromFile(imageFileToShare);
                share.putExtra(Intent.EXTRA_STREAM, uri);

                startActivity(Intent.createChooser(share, "Share Image!"));
            }
        });
    }

    // hỗ trợ cho hình nền wallpaper
    public void GetScreenWidthHeight(){

        displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        height = displayMetrics.heightPixels;

    }

    public void SetBitmapSize(){

        bitmap2 = Bitmap.createScaledBitmap(bitmap1, width, height, false);

    }
    //

    //test instagram

    private boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //Error
        }

        return false;
    }


    private void shareInstagram(File mFileImagePath) {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
        if (intent != null) {
            Intent mIntentShare = new Intent(Intent.ACTION_SEND);
            String mStrExtension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(mFileImagePath).toString());
            String mStrMimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension);
            if (mStrExtension.equalsIgnoreCase("") || mStrMimeType == null) {
                // if there is no extension or there is no definite mimetype, still try to open the file
                mIntentShare.setType("text*//*");
            } else {
                mIntentShare.setType(mStrMimeType);
            }
            mIntentShare.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFileImagePath));
            mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            mIntentShare.setPackage("com.instagram.android");
            startActivity(mIntentShare);
        } else {
            Toast.makeText(ShareActivity.this, "Instagram have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }
    //

}
