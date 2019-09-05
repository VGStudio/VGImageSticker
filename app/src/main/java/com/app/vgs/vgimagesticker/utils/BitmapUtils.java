package com.app.vgs.vgimagesticker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.zomato.photofilters.geometry.Point;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ToneCurveSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

public class BitmapUtils {
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

        }
        return rtnValue;
    }
}
