package com.app.vgs.vgimagesticker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.vo.EffectItem;

import java.util.List;

public class EffectAdapter  extends RecyclerView.Adapter<EffectAdapter.EffectViewHolder> {
    List<EffectItem> mEffectList;
    Context mContext;

    public EffectAdapter(Context context, List<EffectItem> effectList) {
        this.mEffectList = effectList;
        mContext = context;
    }

    @Override
    public EffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_effect_item, parent, false);
        return new EffectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EffectViewHolder holder, int position) {
        try {
            EffectItem effectItem = mEffectList.get(position);
            Drawable drawable = Drawable.createFromStream(mContext.getAssets().open(effectItem.getIconPath()), null);
            holder.mIbIcon.setImageDrawable(drawable);
            holder.mTvName.setText(effectItem.getName());
//            if(position % 2 == 0){
//                holder.mView.setBackgroundColor(Color.RED);
//            }else{
//                holder.mView.setBackgroundColor(Color.BLUE);
//            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }



    @Override
    public int getItemCount() {
        return mEffectList.size();
    }

    public class EffectViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageButton mIbIcon;
        TextView mTvName;
        int mIndex;

        public EffectViewHolder(View view){
            super(view);
            mView = view;
            mIbIcon = view.findViewById(R.id.ibEffectIcon);
            mTvName = view.findViewById(R.id.tvEffectName);
        }


    }
}
