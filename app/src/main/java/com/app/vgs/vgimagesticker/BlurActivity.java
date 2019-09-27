package com.app.vgs.vgimagesticker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.app.vgs.vgimagesticker.vo.DrawingView;

public class BlurActivity extends AppCompatActivity {
    DrawingView mDrawingView;
    SeekBar mSeekBarBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);

        initView();
        setListener();
        initData();

    }


    private void initData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gai_xinh3, options);
        mDrawingView.setBitmap(bitmap);
    }



    private void initView() {
        mDrawingView = findViewById(R.id.drawing);
        mSeekBarBlur = findViewById(R.id.blurseekbar);
    }

    private void setListener(){
        mSeekBarBlur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mDrawingView.setBlurProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
