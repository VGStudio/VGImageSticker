package com.app.vgs.vgimagesticker.vo;

import java.util.ArrayList;
import java.util.List;

public class EffectItem {
    int index;
    String name;
    String iconPath;
    boolean selected = false;

    public EffectItem(int index, String name, String iconPath) {
        this.index = index;
        this.name = name;
        this.iconPath = iconPath;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public static List<EffectItem> getEffectList(){
        List<EffectItem> lst = new ArrayList<>();
        lst.add(new EffectItem(0, "Origin", "icon/effect_icon/icon_effect_origin.webp"));
        lst.add(new EffectItem(1, "Round", "icon/effect_icon/icon_effect_round.png"));
        lst.add(new EffectItem(2, "Black Dots", "icon/effect_icon/icon_effect_blackdots.png"));
        lst.add(new EffectItem(3, "Snow", "icon/effect_icon/icon_effect_snow.png"));
        lst.add(new EffectItem(4, "Tint", "icon/effect_icon/icon_effect_tint.png"));
        lst.add(new EffectItem(5, "Green", "icon/effect_icon/icon_effect_green.png"));
        lst.add(new EffectItem(6, "Cyan", "icon/effect_icon/icon_effect_cyan.png"));
        lst.add(new EffectItem(7, "Yello", "icon/effect_icon/icon_effect_yellow.png"));
        lst.add(new EffectItem(8, "Blue", "icon/effect_icon/icon_effect_blue.png"));
        lst.add(new EffectItem(9, "Gray", "icon/effect_icon/icon_effect_gray.png"));
        lst.add(new EffectItem(10, "Magenta", "icon/effect_icon/icon_effect_magenta.png"));
        lst.add(new EffectItem(11, "Red", "icon/effect_icon/icon_effect_red.png"));
        lst.add(new EffectItem(12, "Indigo", "icon/effect_icon/icon_effect_indigo.png"));
        lst.add(new EffectItem(13, "Purple", "icon/effect_icon/icon_effect_purple.png"));
        lst.add(new EffectItem(14, "Orange", "icon/effect_icon/icon_effect_orange.png"));
        lst.add(new EffectItem(15, "Teal", "icon/effect_icon/icon_effect_teal.png"));
        lst.add(new EffectItem(16, "Lime", "icon/effect_icon/icon_effect_lime.png"));
        lst.add(new EffectItem(17, "Pink", "icon/effect_icon/icon_effect_pink.png"));
        lst.add(new EffectItem(18, "Deep Purple", "icon/effect_icon/icon_effect_deeppurple.png"));
        return lst;
    }
}
