package com.app.vgs.vgimagesticker.utils;

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

    public static Bitmap filterImage(Bitmap bitmap, int index) {
        Bitmap rtnValue = null;
        try {
            Filter myFilter;
            switch (index) {
                case 0:

                    //    ImageLoader.getInstance().displayImage("file:///" + path, imageView);

                    myFilter = new Filter();
                    myFilter.addSubFilter(new SaturationSubFilter(2.3f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 1:

                    //  ImageLoader.getInstance().displayImage("file:///" + path, imageView);

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.7f, 0.0f, 1.0f));
                    rtnValue = myFilter.processFilter(bitmap);
                    break;
                case 2:


                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.2f, 0.2f, 0.0f));
                    rtnValue = myFilter.processFilter(bitmap);


                    break;
                case 3:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ContrastSubFilter(1.2f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 4:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new BrightnessSubFilter(30));
                    rtnValue = myFilter.processFilter(bitmap);


                    break;
                case 5:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.0f, 0.4f, 1.0f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 6:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.5f, 0.5f, 0.5f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 7:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.1f, 1.0f, 0.8f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 8:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.3f, 0.5f, 0.0f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 9:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.8f, 0.0f, 0.5f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 10:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 1.0f, 0.5f, 0.0f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 11:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.0f, 0.0f, 1.0f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 12:
                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 1.0f, 0.5f, 0.0f));
                    rtnValue = myFilter.processFilter(bitmap);
                    break;
                case 13:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.3f, 0.0f, 0.8f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;

                case 14:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ToneCurveSubFilter(new Point[]{new Point(0.0f, 0.0f), new Point(100.0f, 159.0f), new Point(255.0f, 255.0f)}, null, null, null));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 15:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.8f, 0.0f, 0.0f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 16:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ColorOverlaySubFilter(100, 0.0f, 0.6f, 0.0f));
                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 17:

                    myFilter = new Filter();
                    myFilter.addSubFilter(new ToneCurveSubFilter(new Point[]{new Point(0.0f, 0.0f), new Point(100.0f, 200.0f), new Point(255.0f, 255.0f)}, null, null, null));

                    rtnValue = myFilter.processFilter(bitmap);

                    break;
                case 18:
                    myFilter = new Filter();
                    myFilter.addSubFilter(new SaturationSubFilter(4.3f));

                    rtnValue = myFilter.processFilter(bitmap);

                    break;


            }
        } catch (Exception exp) {

        }
        return rtnValue;
    }
}
