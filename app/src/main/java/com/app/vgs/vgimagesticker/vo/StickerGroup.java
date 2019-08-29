package com.app.vgs.vgimagesticker.vo;

import java.util.List;

public class StickerGroup {
    String title;
    String icon;
    List<String> images;

    public StickerGroup(String title, String icon, List<String> images) {
        this.title = title;
        this.icon = icon;
        this.images = images;
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
