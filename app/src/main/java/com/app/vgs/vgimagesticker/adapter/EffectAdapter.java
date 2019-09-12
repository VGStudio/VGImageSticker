package com.app.vgs.vgimagesticker.adapter;

import android.content.Context;
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
    public static final int MODE_EFFECT = 1;
    public static final int MODE_COLOR_EFFECT = 2;



    List<EffectItem> mEffectList;
    Context mContext;
    EffectChooseListner mEffectChooseListener;
    int mEffectMode;

    public EffectAdapter(Context context, List<EffectItem> effectList, EffectChooseListner effectChooseListner, int mode) {
        this.mEffectList = effectList;
        mContext = context;
        mEffectChooseListener = effectChooseListner;
        mEffectMode = mode;
    }

    @Override
    public EffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_effect_item, parent, false);
        return new EffectViewHolder(view);
    }

    public void setSelectedEffectViewState(int position){
        for(int i = 0; i< mEffectList.size(); i++){
            if(i == position){
                mEffectList.get(i).setSelected(true);
            }else{
                mEffectList.get(i).setSelected(false);
            }
        }
    }

    @Override
    public void onBindViewHolder(EffectViewHolder holder, int position) {
        try {
            EffectItem effectItem = mEffectList.get(position);
            Drawable drawable = Drawable.createFromStream(mContext.getAssets().open(effectItem.getIconPath()), null);
            holder.mIbIcon.setImageDrawable(drawable);
            holder.mTvName.setText(effectItem.getName());
            holder.mIndex = position;
            if(effectItem.isSelected()){
                holder.mIbIcon.setBackgroundResource(R.drawable.bg_button_shape);
            }else{
                holder.mIbIcon.setBackgroundResource(R.color.button_default);
            }

        }catch (Exception exp){
            LogUtils.e(exp);
        }

    }



    @Override
    public int getItemCount() {
        return mEffectList.size();
    }

    public class EffectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View mView;
        ImageButton mIbIcon;
        TextView mTvName;
        int mIndex;

        public EffectViewHolder(View view){
            super(view);
            mView = view;
            mIbIcon = view.findViewById(R.id.ibEffectIcon);
            mTvName = view.findViewById(R.id.tvEffectName);
            mIbIcon.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            setSelectedEffectViewState(mIndex);
            mEffectChooseListener.onEffectClick(mIndex, mEffectMode);
        }
    }

    public interface EffectChooseListner{
        void onEffectClick(int position, int mode);
    }
}
