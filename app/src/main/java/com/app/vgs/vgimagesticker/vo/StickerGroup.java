package com.app.vgs.vgimagesticker.vo;

import java.util.List;

public class StickerGroup {
    String id;
    String title;
    String icon;

    List<StickerSubGroup> lstSubGroup;

    public StickerGroup(String id, String title, String icon, List<StickerSubGroup> lstSubGroup) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.lstSubGroup = lstSubGroup;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<StickerSubGroup> getLstSubGroup() {
        return lstSubGroup;
    }

    public void setLstSubGroup(List<StickerSubGroup> lstSubGroup) {
        this.lstSubGroup = lstSubGroup;
    }
}
