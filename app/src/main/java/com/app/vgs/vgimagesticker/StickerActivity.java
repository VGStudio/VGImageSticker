package com.app.vgs.vgimagesticker;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;

import org.json.JSONArray;

import java.util.List;

public class StickerActivity extends AppCompatActivity {
    LinearLayout mSubButton;
    List<StickerGroup> mLstStickerGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        initView();
        initData();

        addView();
    }

    private void initData() {
        mLstStickerGroup = JsonUtils.getStickerGroupFromJsonData(this, "sticker/data.json");
    }

    private void initView() {
        mSubButton = findViewById(R.id.bottom_buttons);


    }

    private void addView(){
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.layout_sub_button, mSubButton, false);
            StickerGroup stickerGroup = null;

            for(StickerGroup stkGroup : mLstStickerGroup){
                if(stkGroup.getId().equals("hairstyle")){
                    stickerGroup = stkGroup;
                }
            }


            for (StickerSubGroup subGroup : stickerGroup.getLstSubGroup()){

                view = layoutInflater.inflate(R.layout.layout_sub_button, mSubButton, false);
                ImageButton imgButton = view.findViewById(R.id.eye_ball);
                TextView textView = view.findViewById(R.id.eye_ball_text);
                Drawable drawable = Drawable.createFromStream(getAssets().open(subGroup.getIcon()), null);
                imgButton.setImageDrawable(drawable);
                textView.setText(subGroup.getTitle());
                mSubButton.addView(view);
            }

        }catch (Exception exp){
            LogUtils.e(exp);
        }





    }


}
