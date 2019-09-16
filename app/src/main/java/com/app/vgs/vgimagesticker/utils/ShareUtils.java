package com.app.vgs.vgimagesticker.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.WallpaperManager;
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
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.app.vgs.vgimagesticker.ReferencesActivity;
import com.app.vgs.vgimagesticker.ShareActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import static com.app.vgs.vgimagesticker.ReferencesActivity.uriReferences;
import static com.app.vgs.vgimagesticker.ShareActivity.intentFB;

public class ShareUtils {

    public ReferencesActivity rfActivity;
    WallpaperManager wallpaperManager;
    DisplayMetrics displayMetrics;
    int width,height;
    Bitmap bitmap1,bitmap2;
    public ShareActivity shareActivity;

    public ShareUtils(ShareActivity shareActivity) {
        this.shareActivity=shareActivity;
    }

    public ShareUtils(ReferencesActivity rfActivity) {
        this.rfActivity=rfActivity;
    }
    public void shareUtils(){
        try {
            Intent share = new Intent(Intent.ACTION_SEND);

            // If you want to share a png image only, you can do:
            // setType("image/png"); OR for jpeg: setType("image/jpeg");
            share.setType("image/*");

            // Make sure you put example png image named myImage.png in your
            // directory
            share.putExtra(Intent.EXTRA_STREAM, uriReferences);
            shareActivity.startActivity(Intent.createChooser(share, "Share Image!"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //
    //////////////////////////

    // click vào wall paper
    public void wallpaperUtils(){
        GetScreenWidthHeight();
        SetBitmapSize();
        wallpaperManager = WallpaperManager.getInstance(shareActivity);
        try {
            wallpaperManager.setBitmap(bitmap2);
            wallpaperManager.suggestDesiredDimensions(width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // hỗ trợ cho hình nền wallpaper
    public void GetScreenWidthHeight(){

        displayMetrics = new DisplayMetrics();

        shareActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        height = displayMetrics.heightPixels;

    }

    public void SetBitmapSize(){

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;
        try {
            InputStream input = shareActivity.getContentResolver().openInputStream(uriReferences);
            bitmap1 = BitmapFactory.decodeStream(input, null, bitmapOptions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap2 = Bitmap.createScaledBitmap(bitmap1, width, height, false);
    }
    //
    /////////////////////

    //test instagram
    public boolean checkAppInstall() {
        String uri = null;
        PackageManager pm = shareActivity.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            //Error
        }

        return false;
    }

    public void shareInstagram(File mFileImagePath) {
        try {
            Intent intent = shareActivity.getPackageManager().getLaunchIntentForPackage("com.instagram.android");
            if (intent != null) {
                intentInstagram();
            } else {
                Toast.makeText(shareActivity, "Instagram have not been installed.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //chuyển địa chỉ tới instagram
    public void intentInstagram(){
        Intent mIntentShare = new Intent(Intent.ACTION_SEND);
        String mStrExtension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(String.valueOf(uriReferences));
        String mStrMimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension);
        if (mStrExtension.equalsIgnoreCase("") || mStrMimeType == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            mIntentShare.setType("text*//*");
        } else {
            mIntentShare.setType(mStrMimeType);
        }
        mIntentShare.putExtra(Intent.EXTRA_STREAM, uriReferences);
        mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        mIntentShare.setPackage("com.instagram.android");
        shareActivity.startActivity(mIntentShare);
    }
    //
    ////////////////////

    // tìm địa chỉ tới nguồn ảnh để liên kết facebook
    @SuppressLint("WrongConstant")
    public void supportDataFacebook(){
        Object uriObj1 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(((StrictMode.VmPolicy.Builder)uriObj1).build());
        if (Build.VERSION.SDK_INT >= 18) {
            ((StrictMode.VmPolicy.Builder)uriObj1).detectFileUriExposure();
        }
        try {
            uriObj1 = Uri.parse((shareActivity).getIntent().getStringExtra("imageToShare-uri"));
            intentFB.setType("image/png");
            intentFB.putExtra("android.intent.extra.STREAM", uriReferences);
            uriObj1 = shareActivity.getPackageManager().queryIntentActivities(intentFB, 0).iterator();
        }catch (Exception e){
            e.printStackTrace();
        }
        while (((Iterator)uriObj1).hasNext())
        {
            Object uriObj2 = (ResolveInfo)((Iterator)uriObj1).next();
            if (((ResolveInfo)uriObj2).activityInfo.name.contains("facebook"))
            {
                uriObj2 = ((ResolveInfo)uriObj2).activityInfo;
                uriObj2 = new ComponentName(((ActivityInfo)uriObj2).applicationInfo.packageName, ((ActivityInfo)uriObj2).name);
                intentFB.addCategory("android.intent.category.LAUNCHER");
                intentFB.setFlags(270532608);
                intentFB.setComponent((ComponentName)uriObj2);
                shareActivity.startActivity(intentFB);
            }
        }
    }

    //hỗ trợ facebook
    public boolean isApplicationSentToBackground(Context paramContext)
    {
        @SuppressLint("WrongConstant") List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1);
        return (!localList.isEmpty()) && (!((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getPackageName().equals(paramContext.getPackageName()));
    }
}
