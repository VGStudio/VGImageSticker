package com.app.vgs.vgimagesticker;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;

import java.util.List;

public class StickerActivity extends AppCompatActivity {
    LinearLayout mLLStickerGroup;
    List<StickerGroup> mLstStickerGroup;
    public static String KEY_GROUP_STICKER_ID = "KEY_GROUP_STICKER_ID";

    private String mStickerId = "";

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
        mStickerId = getIntent().getStringExtra(KEY_GROUP_STICKER_ID);
    }

    private void initView() {
        mLLStickerGroup = findViewById(R.id.llStickerGroup);


    }

    private void addView(){
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            StickerGroup stickerGroup = null;

            for(StickerGroup stkGroup : mLstStickerGroup){
                if(stkGroup.getId().equals(mStickerId)){
                    stickerGroup = stkGroup;
                }
            }

            mLLStickerGroup.setWeightSum(stickerGroup.getLstSubGroup().size());


            for (StickerSubGroup subGroup : stickerGroup.getLstSubGroup()){
                View view = layoutInflater.inflate(R.layout.layout_sub_button, mLLStickerGroup, false);
                ImageButton imgButton = view.findViewById(R.id.ibIcon);
                TextView textView = view.findViewById(R.id.tvDes);
                Drawable drawable = Drawable.createFromStream(getAssets().open(subGroup.getIcon()), null);
                imgButton.setImageDrawable(drawable);
                textView.setText(subGroup.getTitle());
                mLLStickerGroup.addView(view);
            }

        }catch (Exception exp){
            LogUtils.e(exp);
        }





    }


}
