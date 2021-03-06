package com.app.vgs.vgimagesticker.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.app.vgs.vgimagesticker.vo.FrameGroup;
import com.app.vgs.vgimagesticker.vo.FrameSubGroup;
import com.app.vgs.vgimagesticker.vo.MoreAppGroup;
import com.app.vgs.vgimagesticker.vo.StickerGroup;
import com.app.vgs.vgimagesticker.vo.StickerSubGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    /**
     * get data from file frames/data.json
     * @param context
     * @param path
     * @return
     */
    public static List<FrameGroup> getFrameGroupFromJsonData(Context context, String path){
        List<FrameGroup> lstFrameGroup = new ArrayList<FrameGroup>();
        try {
            String jsonData = loadJsonFromAsset(context, path);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int idx=0; idx< jsonArray.length(); idx++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(idx);

                String id = jsonObject.get("id").toString();
                String title = jsonObject.get("title").toString();
                String icon = jsonObject.get("icon").toString();
                JSONArray jsonSubArray = jsonObject.getJSONArray("group");
                List<FrameSubGroup> lstSubGroup = new ArrayList<FrameSubGroup>();
                for(int i = 0; i< jsonSubArray.length(); i++){
                    JSONObject jsSubGroup = (JSONObject) jsonSubArray.get(i);
                    String subId = jsSubGroup.getString("id");
                    String subTitle = jsSubGroup.getString("title");
                    String subIcon = jsSubGroup.getString("icon");
                    String subFolder = jsSubGroup.getString("folder");

                    FrameSubGroup subGroup = new FrameSubGroup(subId, subTitle, subIcon, subFolder);
                    lstSubGroup.add(subGroup);
                }
                FrameGroup stickerGroup = new FrameGroup(id, title, icon, lstSubGroup);
                lstFrameGroup.add(stickerGroup);
            }


        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return lstFrameGroup;
    }


    //MoreApp
    public static List<MoreAppGroup> getMoreAppJsonData(Context context,String path){
        List<MoreAppGroup> lstMoreApp = new ArrayList<>();
        String json = "moreappdatamain.json";
        StringBuilder stringBuilder = new StringBuilder("");
        BufferedReader reader = null;
        String rtn = "";
        int index =0;
        String hinhanh = "";
        String tittle = "";
        String   link = "";
        Bitmap bitmap =null;
        try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open(json)));
                while ((path = reader.readLine()) != null) {
                    stringBuilder.append(path);
                }
                rtn = stringBuilder.toString();
            try {
                JSONObject jsonObject = new JSONObject(rtn);
                JSONArray jsonArray = jsonObject.getJSONArray("apps");
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    hinhanh = jsonObject.getString("icon");
                    tittle  = jsonObject.getString("app_name");
                    link   = jsonObject.getString("link");
                    index   = jsonObject.getInt("index");

                    MoreAppGroup subGroup = new MoreAppGroup(tittle, hinhanh, link);
                    lstMoreApp.add(subGroup);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return lstMoreApp;
    }
    //

    // more app save
    public static List<MoreAppGroup> getMoreAppSaveJsonData(Context context,String path){
        List<MoreAppGroup> lstMoreApp = new ArrayList<>();
        String json = "moreappdatasave.json";
        StringBuilder stringBuilder = new StringBuilder("");
        BufferedReader reader = null;
        String rtn = "";
        int index =0;
        String hinhanh = "";
        String tittle = "";
        String   link = "";
        Bitmap bitmap =null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(json)));
            while ((path = reader.readLine()) != null) {
                stringBuilder.append(path);
            }
            rtn = stringBuilder.toString();
            try {
                JSONObject jsonObject = new JSONObject(rtn);
                JSONArray jsonArray = jsonObject.getJSONArray("apps");
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);
                    hinhanh = jsonObject.getString("icon");
                    tittle  = jsonObject.getString("app_name");
                    link   = jsonObject.getString("link");

                    MoreAppGroup subGroup = new MoreAppGroup(tittle, hinhanh, link);
                    lstMoreApp.add(subGroup);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return lstMoreApp;
    }
    //


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

    public static List<String> getColorListFromJson(Context context){
//        List<String> lstRtn = new ArrayList<>();
//        try {
//            String jsonData = loadJsonFromAsset(context, "color/data.json");
//            JSONArray jsonArray = new JSONArray(jsonData);
//            for(int i=0; i< jsonArray.length(); i++){
//                lstRtn.add(jsonArray.get(i).toString());
//            }
//        }catch (Exception exp){
//            LogUtils.e(exp);
//        }
//        return lstRtn;

        return getColorListFromJson(context, "color/data.json");
    }

    public static List<String> getColorListFromJson(Context context, String path){
        List<String> lstRtn = new ArrayList<>();
        try {
            String jsonData = loadJsonFromAsset(context, path);
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i=0; i< jsonArray.length(); i++){
                lstRtn.add(jsonArray.get(i).toString());
            }
        }catch (Exception exp){
            LogUtils.e(exp);
        }
        return lstRtn;
    }

}
