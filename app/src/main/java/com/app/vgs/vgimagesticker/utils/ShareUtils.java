package com.app.vgs.vgimagesticker.utils;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.app.vgs.vgimagesticker.ReferencesActivity;
import com.app.vgs.vgimagesticker.ShareActivity;

import java.io.IOException;

import static com.app.vgs.vgimagesticker.ShareActivity.imPath;
import static com.app.vgs.vgimagesticker.ShareActivity.imgEditShare;
import static com.app.vgs.vgimagesticker.ShareActivity.imgReferences;


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
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imgReferences);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imPath);
            shareIntent.setType("image/jpeg");
            shareActivity.startActivity(Intent.createChooser(shareIntent, "Share Image!"));
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
 //           wallpaperManager.suggestDesiredDimensions(width, height);

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

//        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//        bitmapOptions.inDither = true; //optional
//        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;
//        try {
//            InputStream input = shareActivity.getContentResolver().openInputStream(Uri.parse(imgReferences));
//            bitmap1 = BitmapFactory.decodeStream(input, null, bitmapOptions);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgEditShare.getDrawable();
        Bitmap bitmapTest = bitmapDrawable.getBitmap();
        bitmap2 = Bitmap.createScaledBitmap(bitmapTest, width, height, false);
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

    public void shareInstagram() {
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
        String mStrExtension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(String.valueOf(imPath));
        String mStrMimeType = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(mStrExtension);
        if (mStrExtension.equalsIgnoreCase("") || mStrMimeType == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            mIntentShare.setType("text*//*");
        } else {
            mIntentShare.setType(mStrMimeType);
        }
        mIntentShare.putExtra(Intent.EXTRA_STREAM, imPath);
        mIntentShare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        mIntentShare.setPackage("com.instagram.android");
        shareActivity.startActivity(mIntentShare);
    }
    //
    ////////////////////


}
