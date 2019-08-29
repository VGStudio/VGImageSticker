package com.app.vgs.vgimagesticker.utils;

import android.content.Context;

import com.app.vgs.vgimagesticker.vo.StickerGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static String loadJsonFromAsset(Context context, String path){
        String json = null;
        try {
            InputStream is = context.getAssets().open(path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");



            LogUtils.d(json);
        } catch (Exception ex) {
            LogUtils.e(ex);
        }
        return json;
    }

    public static StickerGroup getStickerGroupFromJsonData(Context context, String path){
        StickerGroup stickerGroup = null;
        try {
            String jsonData = loadJsonFromAsset(context, path);
            JSONObject jsonObject = new JSONObject(jsonData);
            String titile = jsonObject.get("titile").toString();
            String icon = jsonObject.get("icon").toString();

            JSONArray jsonArray = jsonObject.getJSONArray("images");
            List<String> lstImages = new ArrayList<String>();
            for(int i = 0; i< jsonArray.length(); i++){
                String imgPath = jsonArray.get(i).toString();
                lstImages.add(imgPath);
            }
            stickerGroup = new StickerGroup(titile, icon, lstImages);
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return stickerGroup;
    }
}
