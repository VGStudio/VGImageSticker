package com.app.vgs.vgimagesticker.vo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.app.vgs.vgimagesticker.R;

public class DrawingView extends View {

    // drawing path
    private Path drawPath;
    // drawing and canvas paint
    private Paint drawPaint, canvasPaint;
    // initial color
    private int paintColor = 0xFFC0C0C0, paintAlpha = 1;
    // canvas
    private Canvas drawCanvas;
    // canvas bitmap
    private Bitmap canvasBitmap;
    int originalheight,originalwidth;
    /**
     * @return the scree_w
     */

    private BlurMaskFilter blurMaskFilter;
    public int scree_w, screen_h;

    public void setCanvasBitmap(Bitmap bitmap1, int i, int j, int widthPx, int heightPx) {
        this.canvasBitmap = bitmap1;

        if(i<heightPx&&j<widthPx){
            this.originalheight=i;
            this.originalwidth=j;
        }else{

            if(i>heightPx&&j>widthPx){

                this.originalheight=heightPx-1;
                this.originalwidth=widthPx-1;
            }
            else if(j>widthPx){
                this.originalwidth=widthPx-1;
                this.originalheight=i;

            }
            else{
                this.originalwidth=j;
                this.originalheight=heightPx-1;

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
        setupDrawing();
    }

    // prepare drawing
    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        //

        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(30);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint();
//      BitmapShader patternBMPshader = new BitmapShader(canvasBitmap,
//              Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//      //color and shader
//      drawPaint.setColor(0xFFFFFFFF);
//      drawPaint.setShader(patternBMPshader);

        blurMaskFilter = new BlurMaskFilter( 5,
                BlurMaskFilter.Blur.NORMAL);

        drawPaint.setMaskFilter(blurMaskFilter);
    }

    public void  firstsetupdrawing( Bitmap bitmap){

        drawPath = new Path();
        drawPaint = new Paint();
        //

//      drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(30);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint();
//      BitmapShader patternBMPshader = new BitmapShader(canvasBitmap,
//              Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//      //color and shader
//      drawPaint.setColor(0xFFFFFFFF);
//      drawPaint.setShader(patternBMPshader);

        blurMaskFilter = new BlurMaskFilter( 5,
                BlurMaskFilter.Blur.NORMAL);

        drawPaint.setMaskFilter(blurMaskFilter);
        drawPaint.setColor(paintColor);
//  drawPaint.setAlpha(paintAlpha);
        BitmapShader patternBMPshader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //color and shader
        drawPaint.setColor(0xFFFFFFFF);
        drawPaint.setShader(patternBMPshader);


    }
    // view assigned size
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // int i=canvasBitmap.getHeight();
        // int y=canvasBitmap.getWidth();
        canvasBitmap = Bitmap.createScaledBitmap(canvasBitmap,originalwidth+1,originalheight+1, true);

        drawCanvas = new Canvas(canvasBitmap);
    }

    // draw view
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
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
                /*
                 * if (x == touchX && y == touchY) {
                 *
                 * drawPath.moveTo(touchX + 1, touchY + 1); } else {
                 */

                drawPath.moveTo(touchX, touchY);
                // }
                break;
            case MotionEvent.ACTION_MOVE:
                /*
                 * if (x == touchX && y == touchY) {
                 *
                 * drawPath.lineTo(touchX + 1, touchY + 1);
                 *
                 * } else {
                 */
                drawPath.lineTo(touchX, touchY);
                // }
                break;
            case MotionEvent.ACTION_UP:
                /*
                 * if (x == touchX && y == touchY) {
                 *
                 * drawPath.lineTo(touchX + 1, touchY + 1);
                 * drawCanvas.drawPath(drawPath, drawPaint); drawPath.reset(); }
                 *
                 * else {
                 */

                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                // }
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

    public void setColor(String newColor) {
        invalidate();
        //check whether color value or pattern name
        if(newColor.startsWith("#")){
            paintColor = Color.parseColor(newColor);
            drawPaint.setColor(paintColor);
            drawPaint.setShader(null);
        }
        else{
            //pattern
            int patternID = getResources().getIdentifier(
                    newColor, "drawable", "com.example.drawingfun");
            //decode
//          Bitmap patternBMP = BitmapFactory.decodeResource(getResources(), patternID);
            Bitmap patternBMP = BitmapFactory.decodeResource(getResources(),R.drawable.gai_xinh3);
            //create shader
            BitmapShader patternBMPshader = new BitmapShader(patternBMP,
                    Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            //color and shader
            drawPaint.setColor(0xFFFFFFFF);
            drawPaint.setShader(patternBMPshader);
        }
    }

}