package com.app.vgs.vgimagesticker.vo;

import java.util.List;

public class FrameGroup {
    String id;
    String title;
    String icon;

    List<FrameSubGroup> lstSubGroup;

    public FrameGroup(String id, String title, String icon, List<FrameSubGroup> lstSubGroup) {
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

    public List<FrameSubGroup> getLstSubGroup() {
        return lstSubGroup;
    }

    public void setLstSubGroup(List<FrameSubGroup> lstSubGroup) {
        this.lstSubGroup = lstSubGroup;
    }
}
