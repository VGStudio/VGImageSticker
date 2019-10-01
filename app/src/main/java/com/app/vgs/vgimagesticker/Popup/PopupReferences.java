package com.app.vgs.vgimagesticker.Popup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.ShareActivity;

import java.io.InputStream;

public class PopupReferences extends AppCompatActivity {

    String hinhanh= "";
    ImageView imgEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_references);

        initView();
        initData();
    }

    private void initData() {
        chinhPopup();
        readDataImage();
    }

    private void initView() {
        imgEdit  =  findViewById(R.id.imgListEdit);
    }

    private void chinhPopup(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.4));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    // read data image
    private void readDataImage(){
        try {
            Intent intent = getIntent();
            hinhanh = intent.getStringExtra("listImage");
            try{
                imgEdit.setImageResource(Integer.parseInt(hinhanh));
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mShare(View view) {
        Intent intent = new Intent(PopupReferences.this, ShareActivity.class);
        intent.putExtra("imageEdit2",hinhanh);
        startActivity(intent);
    }

    public void mDelete(View view) {

    }
}
