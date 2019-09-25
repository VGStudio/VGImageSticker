package com.app.vgs.vgimagesticker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.app.vgs.vgimagesticker.vo.DrawingView;

public class BlurActivity extends AppCompatActivity {
    DrawingView mDrawingView;
    int originalheight;
    int originalwidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        initView();
        initData();
    }

    private void initData() {

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gai_xinh3, options);

        int widthPx = getWindowManager().getDefaultDisplay().getWidth();
        int heightPx = getWindowManager().getDefaultDisplay().getHeight();
//
//
//
//        mDrawingView.setCanvasBitmap(bitmap, bitmap.getHeight(), bitmap.getWidth(), widthPx, heightPx);
//        mDrawingView.firstsetupdrawing(bitmap);



        //widthPx = mDrawingView.getLayoutParams().width;
        //heightPx = mDrawingView.getLayoutParams().height;
        // set new height and width to custom class drawing view
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mDrawingView
                .getLayoutParams();

        if (bitmap.getHeight() < heightPx && bitmap.getWidth() < widthPx) {
            params.height = bitmap.getHeight();
            params.width = bitmap.getWidth();
        } else {

            if (bitmap.getHeight() > heightPx && bitmap.getWidth() > widthPx) {

                params.height = heightPx;
                params.width = widthPx;
            } else if (bitmap.getWidth() > widthPx) {
                params.width = widthPx;
                params.height = bitmap.getHeight();

            } else {
                params.width = bitmap.getWidth();
                params.height = heightPx;

            }
        }

        mDrawingView.setLayoutParams(params);
        mDrawingView.setCanvasBitmap(bitmap, bitmap.getHeight(),
                bitmap.getWidth(), widthPx, heightPx);

        if(bitmap.getHeight()<heightPx&&bitmap.getWidth()<widthPx){
            this.originalheight=bitmap.getHeight();
            this.originalwidth=bitmap.getWidth();
        }else{

            if(bitmap.getHeight()>heightPx&&bitmap.getWidth()>widthPx){

                this.originalheight=heightPx;
                this.originalwidth=widthPx;
            }
            else if(bitmap.getWidth()>widthPx){
                this.originalwidth=widthPx;
                this.originalheight=bitmap.getHeight();

            }
            else{
                this.originalwidth=bitmap.getWidth();
                this.originalheight=heightPx;

            }
        }
        Bitmap tem=Bitmap.createScaledBitmap(bitmap, originalwidth,
                originalheight, true);
        Bitmap bitmap2=createBitmap_ScriptIntrinsicBlur(tem,40);
        mDrawingView.firstsetupdrawing(bitmap2);
    }

    private void initView() {
        mDrawingView = findViewById(R.id.drawing);
    }

    public Bitmap createBitmap_ScriptIntrinsicBlur(Bitmap src, int radius) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub

        // Radius range (0 < r <= 25)
    /*  if (r <= 1) {
            r = 1;
        } else if (r > 25) {
            r = 25;
        }

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(this);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript,
                bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();
        return bitmap;*/



//here i can make radius up to 40 use for it below code

        int w = src.getWidth();
        int h = src.getHeight();
        int[] pix = new int[w * h];
        src.getPixels(pix, 0, w, 0, 0, w, h);

        for(int r = radius; r >= 1; r /= 2)
        {
            for(int i = r; i < h - r; i++)
            {
                for(int j = r; j < w - r; j++)
                {
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
                                    +  (br & 0xFF00) + (bc & 0xFF00) + (cl & 0xFF00) + (cr & 0xFF00)) >> 3) & 0xFF00 |
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
}
