package com.app.vgs.vgimagesticker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import com.app.vgs.vgimagesticker.R;

public class Temp {
    Context context;
    private Bitmap getIcon() {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inMutable = true;
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.filter__0, option);
    }

    public static void getFilterIcon(Context context, Bitmap bitmap1){
        try {
            Bitmap bitmapTemp = null;
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("icon/filter_icon/icon_filter_original.webp"));

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);


            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 1);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_firebrick.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 2);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_fuchsia.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 3);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_lightcora.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 4);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_deepcora.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 5);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_thistle.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 6);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_dodgerblue.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 7);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_floralwhite.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 8);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_royalblue.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 9);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_olivedrab.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 10);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_deeppink.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 11);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_golderrod.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 12);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_steelblue.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 13);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_darkorange.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 14);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_magenta.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 15);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_fractal.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 16);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_honeydew.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 17);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_maroon.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 18);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_seegreen.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 19);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_ghostwhite.png");

            bitmapTemp = bitmap.copy(bitmap.getConfig(), true);
            bitmapTemp = BitmapUtils.applyFilterImage(context, bitmapTemp, 20);
            FileUtils.saveBitmapToFile(bitmapTemp, "filter_icon", "icon_filter_darkred.png");
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }
    private void getEffectIcon() {
        try {
            ImageProcess imageProcess = new ImageProcess();

            Bitmap bitmapTemp = null;

            LogUtils.d("Start create round icon");
            Bitmap bitmap = getIcon();
            Bitmap roundBitmap = imageProcess.applyRoundCornerEffect(bitmap, 45.0f);
            FileUtils.saveBitmapToFile(roundBitmap, "effect_icon", "icon_effect_round.png");
            LogUtils.d("Finish create round icon");

            LogUtils.d("Start create black dots icon");
            bitmap = getIcon();
            Bitmap blackDots = imageProcess.applyBlackFilter(bitmap);
            FileUtils.saveBitmapToFile(blackDots, "effect_icon", "icon_effect_blackdots.png");
            LogUtils.d("Finish create black dots icon");

            LogUtils.d("Start create snow icon");
            bitmap = getIcon();
            Bitmap snowBitmap = imageProcess.applySnowEffect(bitmap);
            FileUtils.saveBitmapToFile(snowBitmap, "effect_icon", "icon_effect_snow.png");
            LogUtils.d("Finish create snow icon");

            LogUtils.d("Start create tint icon");
            bitmap = getIcon();
            Bitmap tintBitmap = imageProcess.applyTintEffect(bitmap, 100);
            FileUtils.saveBitmapToFile(tintBitmap, "effect_icon", "icon_effect_tint.png");
            LogUtils.d("Finish create tint icon");

            LogUtils.d("Start create green icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.GREEN);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_green.png");
            LogUtils.d("Finish create green icon");


            LogUtils.d("Start create cyan icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.CYAN);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_cyan.png");
            LogUtils.d("Finish create cyan icon");

            LogUtils.d("Start create YELLOW icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.YELLOW);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_yellow.png");
            LogUtils.d("Finish create YELLOW icon");

            LogUtils.d("Start create blue icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.BLUE);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_blue.png");
            LogUtils.d("Finish create blue icon");

            LogUtils.d("Start create gray icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.GRAY);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_gray.png");
            LogUtils.d("Finish create gray icon");

            LogUtils.d("Start create magenta icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.MAGENTA);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_magenta.png");
            LogUtils.d("Finish create magenta icon");

            LogUtils.d("Start create red icon");
            bitmap = getIcon();
            bitmapTemp = imageProcess.applyShadingFilter(bitmap, Color.RED);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_red.png");
            LogUtils.d("Finish create red icon");

            LogUtils.d("Start create indigo icon");
            Drawable drawable = context.getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#283593"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_indigo.png");
            LogUtils.d("Finish create indigo icon");

            LogUtils.d("Start create Purple icon");
            drawable = context.getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#D500F9"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_purple.png");
            LogUtils.d("Finish create Purple icon");

            LogUtils.d("Start create Orange icon");
            drawable = context.getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#DD2C00"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_orange.png");
            LogUtils.d("Finish create Orange icon");

            LogUtils.d("Start create Teal icon");
            drawable =context. getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#004D40"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_teal.png");
            LogUtils.d("Finish create Teal icon");

            LogUtils.d("Start create Lime icon");
            drawable = context.getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#C0CA33"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_lime.png");
            LogUtils.d("Finish create Lime icon");

            LogUtils.d("Start create Pink icon");
            drawable = context.getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#E91E63"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_pink.png");
            LogUtils.d("Finish create Pink icon");

            LogUtils.d("Start create Deep Purple icon");
            drawable = context.getResources().getDrawable(R.drawable.filter__0);
            drawable.setColorFilter(Color.parseColor("#6200EA"), PorterDuff.Mode.MULTIPLY);
            bitmapTemp = BitmapUtils.convertDrawable2Bitmap(drawable);
            FileUtils.saveBitmapToFile(bitmapTemp, "effect_icon", "icon_effect_deeppurple.png");
            LogUtils.d("Finish create Deep Purple icon");


        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }


    private void getColorEffectIcon() {
        try {
            ImageProcess imgFilter = new ImageProcess();
            String folder = "color_effect_icon";

            Bitmap bitmapTemp = null;
            LogUtils.d("Start");
            Bitmap bitmap = getIcon();

            bitmapTemp = imgFilter.applyBoostEffect(bitmap, 1, 40);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_boostred.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyBoostEffect(bitmap, 2, 30);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_boostgreen.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyBoostEffect(bitmap, 3, 67);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_boostblue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyBrightnessEffect(bitmap, 80);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_brightness.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyColorFilterEffect(bitmap, 255, 0, 0);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_colorred.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyColorFilterEffect(bitmap, 0, 255, 0);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_colorgreen.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyColorFilterEffect(bitmap, 0, 0, 255);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_colorblue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyDecreaseColorDepthEffect(bitmap, 64);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_paintdeep.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyDecreaseColorDepthEffect(bitmap, 32);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_paintlight.png");


            bitmap = getIcon();
            bitmapTemp = imgFilter.applyContrastEffect(bitmap, 25);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_contrast.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyGammaEffect(bitmap, 1.8, 1.8, 1.8);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_gamma.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyGreyscaleEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_grayscale.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyHueFilter(bitmap, 2);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_hue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyInvertEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_invert.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyMeanRemovalEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_mean.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySepiaToningEffect(bitmap, 10, 1.5, 0.6, 0.12);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_sepia.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySepiaToningEffect(bitmap, 10, 0.88, 2.45, 1.43);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_sepiagreen.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySepiaToningEffect(bitmap, 10, 1.2, 0.87, 2.1);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_sepiablue.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyEmbossEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_emboss.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyEngraveEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_engrave.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applyGaussianBlurEffect(bitmap);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_gaussianblur.png");

            bitmap = getIcon();
            bitmapTemp = imgFilter.applySmoothEffect(bitmap, 100);
            FileUtils.saveBitmapToFile(bitmapTemp, folder, "icon_color_effect_smooth.png");


            LogUtils.d("Finish");


        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }
}
