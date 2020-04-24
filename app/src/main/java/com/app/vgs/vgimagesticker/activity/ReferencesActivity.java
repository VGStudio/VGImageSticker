package com.app.vgs.vgimagesticker.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.vgs.vgimagesticker.Classes.EditImage;
import com.app.vgs.vgimagesticker.R;
import com.app.vgs.vgimagesticker.adapter.ListEditImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReferencesActivity extends AppCompatActivity {

    RecyclerView rcvEdit;
    ListEditImageAdapter adapter;
    List<EditImage> imageList;

    ImageView mImgBack;

    public String[] imageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        initView();
        initData();
    }

    private void initData() {
//        readDataImage();
        fillDataImage();
    }

    private void initView() {
        rcvEdit = findViewById(R.id.rcvEditImage);

        mImgBack    = findViewById(R.id.imgBackImageList);
    }

//    public void readDataImage(){
//        try {
//            SharedPreferences sharePr = getPreferences(0);
//            String imgData = sharePr.getString("image", " ");
//            if (imgData.trim().length() < 0) {
//                return;
//            }
//            else {
//                imageArray = imgData.split(";");
////                File imageFileToShare = new File(String.valueOf(imageArray));
////                Uri  uriReferences = Uri.fromFile(imageFileToShare);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public void fillDataImage(){
        imageList = new ArrayList<>();
//        for(int i=0 ; i<imageArray.length ;i++ ){
//            Object obj = imageArray;
//            String hinh = obj.toString();
//            imageList.add(new EditImage(hinh));
//        }
        String hinh1 = String.valueOf(R.drawable.girl1);
        String hinh2 = String.valueOf(R.drawable.girl2);
        String hinh3 = String.valueOf(R.drawable.girl3);
        String hinh4 = String.valueOf(R.drawable.girl4);
        String hinh5 = String.valueOf(R.drawable.girl5);
        String hinh6 = String.valueOf(R.drawable.girl6);
        String hinh7 = String.valueOf(R.drawable.girl7);
        String hinh8 = String.valueOf(R.drawable.girl8);
        String hinh9 = String.valueOf(R.drawable.girl9);
        String hinh10 = String.valueOf(R.drawable.girl10);
        imageList.add(new EditImage(hinh1));
        imageList.add(new EditImage(hinh2));
        imageList.add(new EditImage(hinh3));
        imageList.add(new EditImage(hinh4));
        imageList.add(new EditImage(hinh5));
        imageList.add(new EditImage(hinh6));
        imageList.add(new EditImage(hinh7));
        imageList.add(new EditImage(hinh8));
        imageList.add(new EditImage(hinh9));
        imageList.add(new EditImage(hinh10));
        adapter = new ListEditImageAdapter(getApplicationContext(),imageList);
        rcvEdit.setHasFixedSize(true);
        rcvEdit.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        rcvEdit.setAdapter(adapter);
    }

    public void clickBack(View view) {
        onBackPressed();
    }

}
