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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.app.vgs.vgimagesticker.utils.LogUtils;

public class DrawingView extends View {


    private int mPaintWidth = 40;
    // drawing path
    private Path drawPath;
    // drawing and canvas paint
    private Paint drawPaint, canvasPaint, belowDrawPain;
    // initial color
    private int paintColor = 0xFFC0C0C0, paintAlpha = 255;
    // canvas
    private Canvas drawCanvas;
    // canvas bitmap
    private Bitmap canvasBitmap;
    int originalheight, originalwidth;

    int mCenterX, mCenterY;
    /**
     * @return the scree_w
     */

    Bitmap mBitmap;

    private BlurMaskFilter blurMaskFilter;
    public int scree_w, screen_h;

    public void setCanvasBitmap(Bitmap bitmap1, int i, int j, int widthPx, int heightPx) {
        this.canvasBitmap = bitmap1;

        if (i < heightPx && j < widthPx) {
            this.originalheight = i;
            this.originalwidth = j;
        } else {

            if (i > heightPx && j > widthPx) {

                this.originalheight = heightPx - 1;
                this.originalwidth = widthPx - 1;
            } else if (j > widthPx) {
                this.originalwidth = widthPx - 1;
                this.originalheight = i;

            } else {
                this.originalwidth = j;
                this.originalheight = heightPx - 1;

            }
        }

    }

    public void setScree_w(int width) {
        // TODO Auto-generated method stub
        this.scree_w = width;
    }

    public void setScreen_h(int height) {
        // TODO Auto-generated method stub
        this.screen_h = height;
    }

    // constructor
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //drawCanvas = new Canvas(canvasBitmap);
    }

    public void setBitmap(Bitmap bitmap){
        mBitmap = bitmap;
    }

    // prepare drawing
    private void setupDrawing() {
        LogUtils.d("setupDrawing");
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(mPaintWidth);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint();
        blurMaskFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);

        drawPaint.setMaskFilter(blurMaskFilter);


    }


    public void firstsetupdrawing(Bitmap bitmap) {
        LogUtils.d("firstsetupdrawing");
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(mPaintWidth);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint();
        drawPaint.setAlpha(1);

        blurMaskFilter = new BlurMaskFilter(5,
                BlurMaskFilter.Blur.NORMAL);


        drawPaint.setMaskFilter(blurMaskFilter);
        drawPaint.setColor(paintColor);
        //drawPaint.setColor(Color.parseColor("#8140FF4A"));
        BitmapShader patternBMPshader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        drawPaint.setColor(0xFFFFFFFF);
//        drawPaint.setShader(patternBMPshader);





        belowDrawPain = new Paint();
        belowDrawPain.setAntiAlias(true);
        belowDrawPain.setStrokeWidth(mPaintWidth);
        belowDrawPain.setStyle(Paint.Style.STROKE);
        belowDrawPain.setStrokeJoin(Paint.Join.ROUND);
        belowDrawPain.setStrokeCap(Paint.Cap.ROUND);
        belowDrawPain.setAlpha(255);

        //blurMaskFilter = new BlurMaskFilter(5,
         //       BlurMaskFilter.Blur.NORMAL);


        belowDrawPain.setMaskFilter(blurMaskFilter);
        belowDrawPain.setColor(Color.parseColor("#8140FF4A"));
        belowDrawPain.setShader(patternBMPshader);


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
        LogUtils.d("onSizeChanged");
        float rattion = calScaleRationForDrawable(w, h, mBitmap.getWidth(), mBitmap.getHeight());
        canvasBitmap = Bitmap.createScaledBitmap(mBitmap, (int)(mBitmap.getWidth() * rattion), (int)(mBitmap.getHeight() * rattion), true);

        Bitmap bm = createBitmap_ScriptIntrinsicBlur(canvasBitmap, mPaintWidth);

        drawCanvas = new Canvas(bm);
        mCenterX = (w - canvasBitmap.getWidth())/2;
        mCenterY = (h - canvasBitmap.getHeight())/2;
        firstsetupdrawing(bm);


    }

    public Bitmap createBitmap_ScriptIntrinsicBlur(Bitmap src, int radius) {
        // Radius range (0 < r <= 25)
        //here i can make radius up to 40 use for it below code

        int w = src.getWidth();
        int h = src.getHeight();
        int[] pix = new int[w * h];
        src.getPixels(pix, 0, w, 0, 0, w, h);

        for (int r = radius; r >= 1; r /= 2) {
            for (int i = r; i < h - r; i++) {
                for (int j = r; j < w - r; j++) {
                    int tl = pix[(i - r) * w + j - r];
                    int tr = pix[(i - r) * w + j + r];
                    int tc = pix[(i - r) * w + j];
                    int bl = pix[(i + r) * w + j - r];
                    int br = pix[(i + r) * w + j + r];
                    int bc = pix[(i + r) * w + j];
                    int cl = pix[i * w + j - r];
                    int cr = pix[i * w + j + r];

                    pix[(i * w) + j] = 0xFF000000 |
                            (((tl & 0xFF) + (tr & 0xFF) + (tc & 0xFF) + (bl & 0xFF) +
                                    (br & 0xFF) + (bc & 0xFF) + (cl & 0xFF) + (cr & 0xFF)) >> 3) & 0xFF |
                            (((tl & 0xFF00) + (tr & 0xFF00) + (tc & 0xFF00) + (bl & 0xFF00)
                                    + (br & 0xFF00) + (bc & 0xFF00) + (cl & 0xFF00) + (cr & 0xFF00)) >> 3) & 0xFF00 |
                            (((tl & 0xFF0000) + (tr & 0xFF0000) + (tc & 0xFF0000) +
                                    (bl & 0xFF0000) + (br & 0xFF0000) + (bc & 0xFF0000) + (cl & 0xFF0000) +
                                    (cr & 0xFF0000)) >> 3) & 0xFF0000;
                }
            }
        }
        Bitmap blurred = Bitmap.createBitmap(w, h, src.getConfig());
        blurred.setPixels(pix, 0, w, 0, 0, w, h);
        return blurred;


    }



    // draw view
    @Override
    protected void onDraw(Canvas canvas) {
        LogUtils.d("onDraw");


        canvas.drawBitmap(canvasBitmap, mCenterX, mCenterY, canvasPaint);



        canvas.drawPath(drawPath, belowDrawPain);

    }

    // respond to touch interaction

    float x = 0, y = 0;

    public boolean onTouchEvent(MotionEvent event) {

        Log.e("getalph drawing view", "" + getPaintAlpha());
        float touchX = 0, touchY = 0;
        x = touchX;
        y = touchY;
        touchX = event.getX();

        touchY = event.getY();

        // respond to down, move and up events
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPaint.setColor(Color.parseColor("#8140FF4A"));
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.d("ACTION_UP");
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                //drawPath.reset();
                break;
            default:
                return false;
        }
        // redraw
        invalidate();
        return true;
    }

    // return current alpha
    public int getPaintAlpha() {
        return Math.round((float) paintAlpha / 255 * 40);
    }

    // set alpha
    public void setPaintAlpha(int newAlpha, Bitmap bitmap) {
        paintAlpha = Math.round((float) newAlpha / 40 * 255);
        drawPaint.setColor(paintColor);
        drawPaint.setAlpha(paintAlpha);
        BitmapShader patternBMPshader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //color and shader
        drawPaint.setColor(0xFFFFFFFF);
        drawPaint.setShader(patternBMPshader);
    }


}