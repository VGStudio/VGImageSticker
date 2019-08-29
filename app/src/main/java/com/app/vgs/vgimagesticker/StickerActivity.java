package com.app.vgs.vgimagesticker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class StickerActivity extends AppCompatActivity {
    LinearLayout mSubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        initView();

        addView();
    }

    private void initView() {
        mSubButton = findViewById(R.id.bottom_buttons);


    }

    private void addView(){
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_sub_button, mSubButton, false);
        for(int i =0 ; i< 5; i++){
            view = layoutInflater.inflate(R.layout.layout_sub_button, mSubButton, false);
            ImageButton imgButton = view.findViewById(R.id.eye_ball);
            TextView textView = view.findViewById(R.id.eye_ball_text);
            if(i%2==0){
                imgButton.setImageDrawable(getResources().getDrawable(R.drawable.eye_lens));
                textView.setText("Hair style");
            }else{
            }
            mSubButton.addView(view);
        }


    }


}
