package com.app.vgs.vgimagesticker.utils;

import android.content.Context;

import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;

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
        } catch (Exception ex) {
            LogUtils.e(ex);
        }
        return json;
    }


    /**
     * get data from file sticker/data.json
     * @param context
     * @param path
     * @return
     */
    public static List<StickerGroup> getStickerGroupFromJsonData(Context context, String path){
        List<StickerGroup> lstStickerGroup = new ArrayList<StickerGroup>();
        try {
            String jsonData = loadJsonFromAsset(context, path);
            //JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int idx=0; idx< jsonArray.length(); idx++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(idx);

                String id = jsonObject.get("id").toString();
                String title = jsonObject.get("title").toString();
                String icon = jsonObject.get("icon").toString();
                JSONArray jsonSubArray = jsonObject.getJSONArray("group");
                List<StickerSubGroup> lstSubGroup = new ArrayList<StickerSubGroup>();
                for(int i = 0; i< jsonSubArray.length(); i++){
                    JSONObject jsSubGroup = (JSONObject) jsonSubArray.get(i);
                    String subId = jsSubGroup.getString("id");
                    String subTitle = jsSubGroup.getString("title");
                    String subIcon = jsSubGroup.getString("icon");
                    String subFolder = jsSubGroup.getString("folder");

                    //String id, String title, String icon, String folder
                    StickerSubGroup subGroup = new StickerSubGroup(subId, subTitle, subIcon, subFolder);
                    lstSubGroup.add(subGroup);
                }
                //String id, String title, String icon, List<StickerSubGroup> lstSubGroup
                StickerGroup stickerGroup = new StickerGroup(id, title, icon, lstSubGroup);
                lstStickerGroup.add(stickerGroup);
            }


        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return lstStickerGroup;
    }

    public static StickerGroup getStickerSubGroupFromJsonData(Context context, String path){
        StickerGroup stickerGroup = null;
        try {
            String jsonData = loadJsonFromAsset(context, path);
            JSONObject jsonObject = new JSONObject(jsonData);
            String id = jsonObject.get("id").toString();
            String title = jsonObject.get("title").toString();
            String icon = jsonObject.get("icon").toString();
            JSONArray jsonArray = jsonObject.getJSONArray("group");
            List<String> lstImages = new ArrayList<String>();
            for(int i = 0; i< jsonArray.length(); i++){
                String imgPath = jsonArray.get(i).toString();
                lstImages.add(imgPath);
            }
            //stickerGroup = new StickerGroup(titile, icon, lstImages);
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return stickerGroup;
    }

    public static List<String> getImagesPathFromJson(Context context, String path){
        List<String> lstRtn = new ArrayList<>();
        try {
            String jsonData = loadJsonFromAsset(context, path);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray("images");
            for(int i=0; i< jsonArray.length(); i++){
                lstRtn.add(jsonArray.get(i).toString());
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return lstRtn;
    }
}
