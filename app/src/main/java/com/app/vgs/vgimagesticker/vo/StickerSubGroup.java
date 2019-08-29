package com.app.vgs.vgimagesticker.vo;

import java.util.List;

public class StickerSubGroup {
    String id;
    String title;
    String icon;
    String folder;
    List<String> images;

    public StickerSubGroup(String id, String title, String icon, String folder, List<String> images) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.folder = folder;
        this.images = images;
    }

    public StickerSubGroup(String id, String title, String icon, String folder) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.folder = folder;
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

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
