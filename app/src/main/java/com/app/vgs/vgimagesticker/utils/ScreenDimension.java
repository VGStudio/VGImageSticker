package com.app.vgs.vgimagesticker.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class ScreenDimension {

    public static int mWidth;
    public static int mHeight;

    public static void getScreenSize(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mHeight = size.y;
    }



}
