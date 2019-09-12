package com.app.vgs.vgimagesticker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.app.vgs.vgimagesticker.R;
import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

public class BitmapUtils {
    public static final String[] FILTER_LIST = new String[]{"Original", "Fire Brick", "Fuchsia", "Light Cora", "Deep Cora", "Thistle", "Dodger Blue", "Floral White", "Royal Blue", "Olive Drab", "Deep Pink", "Golder Rod", "Steel Blue", "Dark Orange", "Magenta", "Fractal", "Honey Dew", "Maroon", "See Green", "Ghost White", "Dark Red"};

    public static Bitmap changeSaturation(Bitmap paramBitmap, float saturation) {
        Bitmap bitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(saturation);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(paramBitmap, 0.0F, 0.0F, paint);
        return bitmap;
    }

    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness) {
        try {
            ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                    contrast, 0.0F, 0.0F, 0.0F, brightness, 0.0F, contrast, 0.0F, 0.0F, brightness,
                    0.0F, 0.0F, contrast, 0.0F, brightness, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F});

            Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

            Canvas canvas = new Canvas(bitmap);

            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(bmp, 0, 0, paint);
            return bitmap;
        } catch (Exception exp) {
            LogUtils.e(exp);
            return null;
        }

    }

    public static Bitmap applyEffectImage(Context context, Bitmap bitmap, int index){
        Bitmap rtnValue = null;
        try {
            Drawable drawable = drawable = new BitmapDrawable(context.getResources(), bitmap);
            ImageProcess imageProcess = new ImageProcess();
            switch ( index){
                case 1:
                    rtnValue = imageProcess.applyRoundCornerEffect(bitmap, 45.0f);
                    break;
                case 2:
                    rtnValue = imageProcess.applyBlackFilter(bitmap);
                    break;
                case 3:
                    rtnValue = imageProcess.applySnowEffect(bitmap);
                    break;
                case 4:
                    rtnValue = imageProcess.applyTintEffect(bitmap, 100);
                    break;
                case 5:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.GREEN);
                    break;
                case 6:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.CYAN);
                    break;
                case 7:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.YELLOW);
                    break;
                case 8:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.BLUE);
                    break;
                case 9:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.GRAY);
                    break;
                case 10:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.MAGENTA);
                    break;
                case 11:
                    rtnValue = imageProcess.applyShadingFilter(bitmap, Color.RED);
                    break;
                case 12:
                    drawable.setColorFilter(Color.parseColor("#283593"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                case 13:
                    drawable.setColorFilter(Color.parseColor("#D500F9"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                case 14:
                    drawable.setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                case 15:
                    drawable.setColorFilter(Color.parseColor("#004D40"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                case 16:
                    drawable.setColorFilter(Color.parseColor("#C0CA33"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                case 17:
                    drawable.setColorFilter(Color.parseColor("#E91E63"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                case 18:
                    drawable.setColorFilter(Color.parseColor("#6200EA"), PorterDuff.Mode.MULTIPLY);
                    rtnValue = BitmapUtils.convertDrawable2Bitmap(drawable);
                    break;
                default:
                    rtnValue = bitmap;
                    break;
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return rtnValue;
    }

    public static Bitmap filterImage(Context context, Bitmap bitmap, int index) {
        Bitmap rtnValue = null;
        try {
            Filter filter = new Filter();
            switch (index) {
                case 1:
                    filter.addSubFilter(new SaturationSubFilter(2.3f));
                    break;
                case 2:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.7f, 0.0f, 1.0f));
                    break;
                case 3:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.2f, 0.2f, 0.0f));
                    break;
                case 4:
                    filter.addSubFilter(new ContrastSubFilter(1.2f));
                    break;
                case 5:
                    filter.addSubFilter(new BrightnessSubFilter(30));
                    break;
                case 6:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.0f, 0.4f, 1.0f));
                    break;
                case 7:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.5f, 0.5f, 0.5f));
                    break;
                case 8:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.1f, 1.0f, 0.8f));
                    break;
                case 9:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.3f, 0.5f, 0.0f));
                    break;
                case 10:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.8f, 0.0f, 0.5f));
                    break;
                case 11:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 1.0f, 0.5f, 0.0f));
                    break;
                case 12:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.0f, 0.0f, 1.0f));
                    break;
                case 13:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 1.0f, 0.5f, 0.0f));
                    break;
                case 14:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.3f, 0.0f, 0.8f));
                    break;
                case 15:
                    filter.addSubFilter(new VignetteSubFilter(context, 100));
                    break;
                case 16:
                    filter.addSubFilter(new ToneCurveSubFilter(new Point[]{new Point(0.0f, 0.0f), new Point(100.0f, 159.0f), new Point(255.0f, 255.0f)}, null, null, null));
                    break;
                case 17:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.8f, 0.0f, 0.0f));
                    break;
                case 18:
                    filter.addSubFilter(new ColorOverlaySubFilter(100, 0.0f, 0.6f, 0.0f));
                    break;
                case 19:
                    filter.addSubFilter(new ToneCurveSubFilter(new Point[]{new Point(0.0f, 0.0f), new Point(100.0f, 200.0f), new Point(255.0f, 255.0f)}, null, null, null));
                    break;
                case 20:
                    filter.addSubFilter(new SaturationSubFilter(4.3f));
                    break;
                default:
                    filter = null;
                    rtnValue = bitmap;
                    break;


            }
            if (filter != null) {
                rtnValue = filter.processFilter(bitmap);
            }
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
        return rtnValue;
    }

    public static Bitmap convertDrawable2Bitmap(@NonNull Drawable drawable){
        try {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0,0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return null;
    }
}
