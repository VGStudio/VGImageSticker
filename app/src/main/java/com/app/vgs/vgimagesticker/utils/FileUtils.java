package com.app.vgs.vgimagesticker.utils;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class FileUtils {
    public static final String FOLDER = "/app_sticker";
    public static final String FOLDER_STICKER_TEMP = FOLDER + "/sticker_temp";
    public static final String IMAGE_FILE_PATTERN = "sticker";

    public Uri saveBitmapShare(Bitmap bitmap, Context context) {
        Uri rtnValue = null;
        StringBuilder strBuilder;
        if (bitmap != null && !bitmap.isRecycled()) {
            strBuilder = new StringBuilder();
            strBuilder.append(Environment.getExternalStorageDirectory());
            strBuilder.append(FOLDER);
            File file = new File(strBuilder.toString());

            if (!file.exists())
                file.mkdirs();

            file = new File(file, String.format("%s_%d.png", new Object[]{"img_sticker", System.currentTimeMillis()}));
            if (file.exists() && file.delete())
                try {
                    file.createNewFile();
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                } catch (Exception exp2) {
                }
            } catch (Exception exp) {

            } finally {
            }
            ContentValues contentValues = new ContentValues(3);
            contentValues.put("title", IMAGE_FILE_PATTERN);
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("_data", file.getAbsolutePath());

            rtnValue = Uri.fromFile(file.getAbsoluteFile());

            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        return rtnValue;
    }

    public static Uri saveBitmap(Bitmap bitmap, Context context) {
        if (bitmap == null) {
            return null;
        }
        Uri rtnValue = null;
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(Environment.getExternalStorageDirectory());
            strBuilder.append("/" + FOLDER + "/Temp");
            File folder = new File(strBuilder.toString());
            if (folder.isDirectory()) {
                String[] arrayOfString = folder.list();
                //delete all file in temp folder
                for (int i = 0; i < arrayOfString.length; i++) {
                    (new File(folder, arrayOfString[i])).delete();
                }

            }
            strBuilder = new StringBuilder();
            if (!bitmap.isRecycled()) {
                strBuilder.append(Environment.getExternalStorageDirectory());
                strBuilder.append("/" + FOLDER + "/Temp");
                folder = new File(strBuilder.toString());
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                File imgFile = new File(folder, String.format("%s_%d.png", new Object[]{"temp", System.currentTimeMillis()}));
                if (imgFile.exists()) {
                    imgFile.delete();
                }

                imgFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(imgFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);


                ContentValues contentValues = new ContentValues(3);
                contentValues.put("title", "temp");
                contentValues.put("mime_type", "image/jpeg");
                contentValues.put("_data", imgFile.getAbsolutePath());
                rtnValue = Uri.fromFile(imgFile.getAbsoluteFile());
                context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            }
        } catch (IOException exp) {
            LogUtils.e(exp);
            return null;
        }

        return rtnValue;
    }

    public static File createEmptyFile(Context context) {
        File rtnValue = null;
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(Environment.getExternalStorageDirectory());
            strBuilder.append(FOLDER_STICKER_TEMP);
            File folder = new File(strBuilder.toString());
            if (folder.isDirectory()) {
                String[] arrayOfString = folder.list();
                //delete all file in temp folder
                for (int i = 0; i < arrayOfString.length; i++) {
                    (new File(folder, arrayOfString[i])).delete();
                }
            }
            if (!folder.exists()) {
                folder.mkdirs();
            }
            rtnValue = new File(folder, String.format("%s_%d.png", new Object[]{"temp_sticker", System.currentTimeMillis()}));
            if (rtnValue.exists()) {
                rtnValue.delete();
            }
            //rtnValue.createNewFile();

        } catch (Exception exp) {
            LogUtils.e(exp);
        }
        return rtnValue;
    }
}
