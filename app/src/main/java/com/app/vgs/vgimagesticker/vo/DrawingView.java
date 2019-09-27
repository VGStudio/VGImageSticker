package com.app.vgs.vgimagesticker.vo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.commit451.nativestackblur.NativeStackBlur;

public class DrawingView extends View {


    private int mPaintWidth = 40;
    // drawing path
    private Path mDrawPath;
    // drawing and canvas paint
    private Paint mDrawPaint, mCanvasPaint, mBelowDrawPain;
    // canvas
    private Canvas mDrawCanvas;
    //private Canvas mDrawCanvas2;
    // canvas bitmap
    private Bitmap mCanvasBitmap, mBitmapWithDrawedSize;

    private Bitmap mBitmapBlur;

    private int mBlurProgress = 30;

    int mStartX, mStartY;
    /**
     * @return the scree_w
     */

    Bitmap mBitmap;

    private BlurMaskFilter mBlurMaskFilter;

    // constructor
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }


    public void firstsetupdrawing(Bitmap bitmapTemp) {
        LogUtils.d("firstsetupdrawing");
        mCanvasPaint = new Paint();
        mBlurMaskFilter = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);

        setFocusable(true);
        setFocusableInTouchMode(true);

        mDrawPath = new Path();
        mDrawPaint = new Paint();
        mDrawPaint.setAntiAlias(true);
        mDrawPaint.setStrokeWidth(mPaintWidth);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawPaint.setAlpha(255);
        mDrawPaint.setMaskFilter(mBlurMaskFilter);
        mDrawPaint.setColor(-1);
        BitmapShader patternBMPshader = new BitmapShader(bitmapTemp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mDrawPaint.setShader(patternBMPshader);


        mBelowDrawPain = new Paint();
        mBelowDrawPain.setAntiAlias(true);
        mBelowDrawPain.setStrokeWidth(mPaintWidth);
        mBelowDrawPain.setStyle(Paint.Style.STROKE);
        mBelowDrawPain.setStrokeJoin(Paint.Join.ROUND);
        mBelowDrawPain.setStrokeCap(Paint.Cap.ROUND);
        mBelowDrawPain.setColor(Color.parseColor("#8140FF4A"));


    }

    public void setBlurProgress(int blurProgress){
        try {
            if(mDrawPaint == null){
                return;
            }
            if(mBitmapWithDrawedSize == null){
                return;
            }
            mBlurProgress = blurProgress;
            mBitmapBlur.recycle();

            Bitmap blurBitmap = blurBitmap(mBitmapWithDrawedSize, mBlurProgress);
            mBitmapBlur = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mBitmapBlur);
            canvas.drawBitmap(blurBitmap, mStartX, mStartY, new Paint());
            BitmapShader patternBMPshader = new BitmapShader(mBitmapBlur, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mDrawPaint.setShader(patternBMPshader);
            blurBitmap.recycle();
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }

    private float calScaleRationForDrawable(int imageViewWidth, int imageViewHeight, int drawableWidth, int drawableHeight) {
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

    // view assigned size
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        try {
            mBitmapWithDrawedSize = getBitmapWithDrawedSizeInView(mBitmap, w, h);
            Bitmap bitmapBlur = blurBitmap(mBitmapWithDrawedSize, mBlurProgress);

            mStartX = (w - mBitmapWithDrawedSize.getWidth()) / 2;
            mStartY = (h - mBitmapWithDrawedSize.getHeight()) / 2;

            Bitmap bitmapTemp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvasTemp = new Canvas(bitmapTemp);
            canvasTemp.drawBitmap(mBitmapWithDrawedSize, mStartX, mStartY, new Paint());
            mCanvasBitmap = bitmapTemp;
            mDrawCanvas = new Canvas(mCanvasBitmap);


            mBitmapBlur = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mBitmapBlur);
            canvas.drawBitmap(bitmapBlur, mStartX, mStartY, new Paint());
            firstsetupdrawing(mBitmapBlur);

            bitmapBlur.recycle();
        }catch (Exception exp){
            LogUtils.e(exp);
        }


    }

    private Bitmap getBitmapWithDrawedSizeInView(@NonNull Bitmap bitmap, int viewWidth, int viewHeight){
        try {
            float rattion = calScaleRationForDrawable(viewWidth, viewHeight, bitmap.getWidth(), bitmap.getHeight());
            Bitmap rtnBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (bitmap.getWidth() * rattion), (int) (bitmap.getHeight() * rattion), true);
            return rtnBitmap;
        }catch (Exception exp){
            LogUtils.e(exp);
            return null;
        }
    }

    private Bitmap blurBitmap(Bitmap bitmap, int blurProgress){
        return NativeStackBlur.process(bitmap, blurProgress);
    }




    // draw view
    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.d("onDraw");
        canvas.drawBitmap(mCanvasBitmap, 0, 0, mCanvasPaint);
        canvas.drawPath(mDrawPath, mBelowDrawPain);

    }


    public boolean onTouchEvent(MotionEvent event) {
        float touchX = 0, touchY = 0;
        touchX = event.getX();
        touchY = event.getY();

        // respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("ACTION_UP");
                mDrawPath.lineTo(touchX, touchY);
                mDrawCanvas.drawPath(mDrawPath, mDrawPaint);
                mDrawPath.reset();
                break;
            default:
                return false;
        }
        // redraw
        invalidate();
        return true;
    }


}