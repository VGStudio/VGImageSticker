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

    public static Bitmap applyColorEffectImage(Bitmap bitmap, int index){
        Bitmap rtnValue = null;
        try {
            ImageProcess imageProcess = new ImageProcess();
            switch ( index){
                case 1:
                    rtnValue = imageProcess.applyBoostEffect(bitmap, 1, 40);;
                    break;
                case 2:
                    rtnValue = imageProcess.applyBoostEffect(bitmap, 2, 30);
                    break;
                case 3:
                    rtnValue = imageProcess.applyBoostEffect(bitmap, 3, 67);
                    break;
                case 4:
                    rtnValue = imageProcess.applyBrightnessEffect(bitmap, 80);;
                    break;
                case 5:
                    rtnValue =  imageProcess.applyColorFilterEffect(bitmap, 255, 0, 0);
                    break;
                case 6:
                    rtnValue = imageProcess.applyColorFilterEffect(bitmap, 0, 255, 0);
                    break;
                case 7:
                    rtnValue = imageProcess.applyColorFilterEffect(bitmap, 0, 0, 255);
                    break;
                case 8:
                    rtnValue = imageProcess.applyDecreaseColorDepthEffect(bitmap, 64);
                    break;
                case 9:
                    rtnValue = imageProcess.applyDecreaseColorDepthEffect(bitmap, 32);
                    break;
                case 10:
                    rtnValue = imageProcess.applyContrastEffect(bitmap, 25);
                    break;
                case 11:
                    rtnValue = imageProcess.applyGammaEffect(bitmap, 1.8, 1.8, 1.8);
                    break;
                case 12:
                    rtnValue = imageProcess.applyGreyscaleEffect(bitmap);
                    break;
                case 13:
                    rtnValue = imageProcess.applyHueFilter(bitmap, 2);
                    break;
                case 14:
                    rtnValue = imageProcess.applyInvertEffect(bitmap);
                    break;
                case 15:
                    rtnValue = imageProcess.applyMeanRemovalEffect(bitmap);
                    break;
                case 16:
                    rtnValue = imageProcess.applySepiaToningEffect(bitmap, 10, 1.5, 0.6, 0.12);
                    break;
                case 17:
                    rtnValue = imageProcess.applySepiaToningEffect(bitmap, 10, 0.88, 2.45, 1.43);
                    break;
                case 18:
                    rtnValue = imageProcess.applySepiaToningEffect(bitmap, 10, 1.2, 0.87, 2.1);
                    break;
                case 19:
                    rtnValue = imageProcess.applyEmbossEffect(bitmap);
                    break;
                case 20:
                    rtnValue = imageProcess.applyEngraveEffect(bitmap);
                    break;
                case 21:
                    rtnValue = imageProcess.applyGaussianBlurEffect(bitmap);
                    break;
                case 22:
                    rtnValue = imageProcess.applySmoothEffect(bitmap, 100);
                    break;

                default:
                    rtnValue = bitmap;
                    break;
            }
        }catch (Exception exp){
            rtnValue = bitmap;
            LogUtils.e(exp);
        }
        return rtnValue;
    }

    public static Bitmap applyFilterImage(Context context, Bitmap bitmap, int index) {
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

    public static Bitmap resizeBitmap(@NonNull Bitmap bitmap, int viewWidth, int viewHeight) {
        try {
            float rattion = calScaleRationForDrawable(viewWidth, viewHeight, bitmap.getWidth(), bitmap.getHeight());
            Bitmap rtnBitmap = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * rattion), (int) (bitmap.getHeight() * rattion), true);
            return rtnBitmap;
        } catch (Exception exp) {
            LogUtils.e(exp);
            return null;
        }
    }

    public  static float calScaleRationForDrawable(int imageViewWidth, int imageViewHeight, int drawableWidth, int drawableHeight) {
        float scaleRatio = 1;
        try {
            if (drawableWidth > drawableHeight) {
                scaleRatio = ((float) imageViewWidth) / ((float) drawableWidth);
                // neu drawableHeight * scale > imageViewHeight, tinh lai scale theo height
                if (drawableHeight * scaleRatio > imageViewHeight) {
                    float scaleRation2 = ((float) imageViewHeight) / ((float) (drawableHeight * scaleRatio));
                    scaleRatio = scaleRation2 * scaleRatio;

                }

            } else {
                scaleRatio = ((float) imageViewHeight) / ((float) drawableHeight);
                // neu drawableWidth * scale > imageViewWidth, tinh lai scale theo width
                if (drawableWidth * scaleRatio > imageViewWidth) {
                    float scaleRation2 = ((float) imageViewWidth) / ((float) (drawableWidth * scaleRatio));
                    scaleRatio = scaleRation2 * scaleRatio;

                }
            }
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
        return scaleRatio;

    }
}
