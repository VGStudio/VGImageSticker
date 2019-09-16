package com.app.vgs.vgimagesticker;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.vgs.vgimagesticker.Classes.EditImage;
import com.app.vgs.vgimagesticker.adapter.ListEditImageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReferencesActivity extends AppCompatActivity {

    public static Uri uriReferences;
    RecyclerView rcvEdit;
    ListEditImageAdapter adapter;
    List<EditImage> imageList;

    public String[] imageArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_references);

        initView();
        initData();
    }

    private void initData() {
        readDataImage();
        fillDataImage();
    }

    private void initView() {
        rcvEdit = findViewById(R.id.rcvEditImage);

    }

    public void readDataImage(){
        try {
            SharedPreferences sharePr = getPreferences(0);
            String imgData = sharePr.getString("image", " ");
            if (imgData.trim().length() < 0) {
                return;
            }
            else {
                imageArray = imgData.split(";");
                File imageFileToShare = new File(String.valueOf(imageArray));
                uriReferences = Uri.fromFile(imageFileToShare);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void fillDataImage(){
        imageList = new ArrayList<>();
        for(int i=0 ; i<imageArray.length ;i++ ){
            Object obj = imageArray;
            String hinh = obj.toString();
            imageList.add(new EditImage(hinh));
        }
        adapter = new ListEditImageAdapter(getApplicationContext(),imageList);
        rcvEdit.setHasFixedSize(true);
        rcvEdit.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        rcvEdit.setAdapter(adapter);
    }
}
