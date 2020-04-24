package com.app.vgs.vgimagesticker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.utils.LogUtils;

import java.util.List;

public class FrameAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mLstFramePaths;

    // 1
    public FrameAdapter(Context context, List<String> lstFramePaths) {
        this.mContext = context;
        this.mLstFramePaths = lstFramePaths;
    }

    // 2
    @Override
    public int getCount() {
        return mLstFramePaths.size();
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
            final String framePath = mLstFramePaths.get(position);
            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                convertView = layoutInflater.inflate(R.layout.frame_item, null);
            }

            final ImageView imageView = convertView.findViewById(R.id.ivFrame);
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getAssets().open(framePath));
            imageView.setTag(framePath);
            imageView.setImageBitmap(bitmap);
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
        return convertView;
    }
}
