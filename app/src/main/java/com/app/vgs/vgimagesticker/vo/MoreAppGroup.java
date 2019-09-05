package com.app.vgs.vgimagesticker.vo;

public class MoreAppGroup {
    String tittle;
    String icon;
    String link;

    public MoreAppGroup(String tittle, String icon, String link) {
        this.tittle = tittle;
        this.icon = icon;
        this.link = link;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
