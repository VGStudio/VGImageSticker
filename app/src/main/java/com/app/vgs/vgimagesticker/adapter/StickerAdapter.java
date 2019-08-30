package com.app.vgs.vgimagesticker.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.utils.LogUtils;

import java.util.List;

public class StickerAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mLstStickerPaths;

    // 1
    public StickerAdapter(Context context, List<String> lstStickerPaths) {
        this.mContext = context;
        this.mLstStickerPaths = lstStickerPaths;
    }

    // 2
    @Override
    public int getCount() {
        return mLstStickerPaths.size();
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final String stickerPath = mLstStickerPaths.get(position);
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(R.layout.sticker_item, null);
            }

            final ImageView imageView = (ImageView)convertView.findViewById(R.id.ivSticker);
            Drawable d = Drawable.createFromStream(mContext.getAssets().open(stickerPath), null);
            imageView.setTag(stickerPath);
            imageView.setImageDrawable(d);
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return convertView;
    }
}
