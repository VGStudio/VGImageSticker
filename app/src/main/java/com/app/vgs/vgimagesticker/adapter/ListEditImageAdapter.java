package com.app.vgs.vgimagesticker.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.vgs.vgimagesticker.Classes.EditImage;
import com.app.vgs.vgimagesticker.Popup.PopupReferences;
import com.app.vgs.vgimagesticker.R;

import java.util.List;


public class ListEditImageAdapter extends RecyclerView.Adapter<ListEditImageAdapter.Itemholder2> {

    private Context context;
    private List<EditImage> editImageList;

    public ListEditImageAdapter(Context context, List<EditImage> editImages) {
        this.context = context;
        this.editImageList = editImages;
    }

    @NonNull
    @Override
    public Itemholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_edit_adapter, null);
        Itemholder2 itemholder2 = new Itemholder2(v);
        return itemholder2;
    }

    @Override
    public void onBindViewHolder(@NonNull Itemholder2 holder, int position) {
        final EditImage editImage = editImageList.get(position);
        final String hinhanh = editImage.getHinh();
        holder.imgEdit.setImageResource(Integer.parseInt(hinhanh));
//        final String imgEdit = editImage.getHinh();
//        try {
////            InputStream inputStream = context.getAssets().open(imgEdit);
////            holder.imgEdit.setImageDrawable(Drawable.createFromStream(inputStream, null));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PopupReferences.class);
                intent.putExtra("listImage", hinhanh);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return editImageList.size();
    }

    public class Itemholder2 extends RecyclerView.ViewHolder {

        private ImageView imgEdit;

        public Itemholder2(View itemView) {
            super(itemView);
            imgEdit = (ImageView) itemView.findViewById(R.id.imgEditImage);
        }
    }

}
