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

    public static List<EffectItem> getColorEffectList(){
        List<EffectItem> lst = new ArrayList<>();
        lst.add(new EffectItem(0, "Origin", "icon/color_effect_icon/icon_color_effect_origin.webp"));
        lst.add(new EffectItem(1, "Boost Red", "icon/color_effect_icon/icon_color_effect_boostred.png"));
        lst.add(new EffectItem(2, "Boost Green", "icon/color_effect_icon/icon_color_effect_boostgreen.png"));
        lst.add(new EffectItem(3, "Boost Blue", "icon/color_effect_icon/icon_color_effect_boostblue.png"));
        lst.add(new EffectItem(4, "Brightness", "icon/color_effect_icon/icon_color_effect_brightness.png"));
        lst.add(new EffectItem(5, "Color Red", "icon/color_effect_icon/icon_color_effect_colorred.png"));
        lst.add(new EffectItem(6, "Color Green", "icon/color_effect_icon/icon_color_effect_colorgreen.png"));
        lst.add(new EffectItem(7, "Color Blue", "icon/color_effect_icon/icon_color_effect_colorblue.png"));
        lst.add(new EffectItem(8, "Paint Depth", "icon/color_effect_icon/icon_color_effect_paintdeep.png"));
        lst.add(new EffectItem(9, "Paint Light", "icon/color_effect_icon/icon_color_effect_paintlight.png"));
        lst.add(new EffectItem(10, "Contrast", "icon/color_effect_icon/icon_color_effect_contrast.png"));
        lst.add(new EffectItem(11, "Gama", "icon/color_effect_icon/icon_color_effect_gamma.png"));
        lst.add(new EffectItem(12, "Gray Scale", "icon/color_effect_icon/icon_color_effect_grayscale.png"));
        lst.add(new EffectItem(13, "Hue", "icon/color_effect_icon/icon_color_effect_hue.png"));
        lst.add(new EffectItem(14, "Invert", "icon/color_effect_icon/icon_color_effect_invert.png"));
        lst.add(new EffectItem(15, "Mean", "icon/color_effect_icon/icon_color_effect_mean.png"));
        lst.add(new EffectItem(16, "Sepia", "icon/color_effect_icon/icon_color_effect_sepia.png"));
        lst.add(new EffectItem(17, "Sepia Green", "icon/color_effect_icon/icon_color_effect_sepiagreen.png"));
        lst.add(new EffectItem(18, "Sepia Blue", "icon/color_effect_icon/icon_color_effect_sepiablue.png"));
        lst.add(new EffectItem(19, "Emboss", "icon/color_effect_icon/icon_color_effect_emboss.png"));
        lst.add(new EffectItem(20, "Engrave", "icon/color_effect_icon/icon_color_effect_engrave.png"));
        lst.add(new EffectItem(21, "Gaussian Blur", "icon/color_effect_icon/icon_color_effect_gaussianblur.png"));
        lst.add(new EffectItem(22, "Smooth", "icon/color_effect_icon/icon_color_effect_smooth.png"));
        return lst;
    }

    public static List<EffectItem> getFilterList(){
        List<EffectItem> lst = new ArrayList<>();
        lst.add(new EffectItem(0, "Origin", "icon/filter_icon/icon_filter_original.webp"));
        lst.add(new EffectItem(1, "Fire Brick", "icon/filter_icon/icon_filter_firebrick.png"));
        lst.add(new EffectItem(2, "Fuchsia", "icon/filter_icon/icon_filter_fuchsia.png"));
        lst.add(new EffectItem(3, "Light Cora", "icon/filter_icon/icon_filter_lightcora.png"));
        lst.add(new EffectItem(4, "Deep Cora", "icon/filter_icon/icon_filter_deepcora.png"));
        lst.add(new EffectItem(5, "Thistle", "icon/filter_icon/icon_filter_thistle.png"));
        lst.add(new EffectItem(6, "Dodger Blue", "icon/filter_icon/icon_filter_dodgerblue.png"));
        lst.add(new EffectItem(7, "Floral White", "icon/filter_icon/icon_filter_floralwhite.png"));
        lst.add(new EffectItem(8, "Royal Blue", "icon/filter_icon/icon_filter_royalblue.png"));
        lst.add(new EffectItem(9, "Olive Drab", "icon/filter_icon/icon_filter_olivedrab.png"));
        lst.add(new EffectItem(10, "Deep Pink", "icon/filter_icon/icon_filter_deeppink.png"));
        lst.add(new EffectItem(11, "Golder Rod", "icon/filter_icon/icon_filter_golderrod.png"));
        lst.add(new EffectItem(12, "Steel Blue", "icon/filter_icon/icon_filter_steelblue.png"));
        lst.add(new EffectItem(13, "Dark Orange", "icon/filter_icon/icon_filter_darkorange.png"));
        lst.add(new EffectItem(14, "Magenta", "icon/filter_icon/icon_filter_magenta.png"));
        lst.add(new EffectItem(15, "Fractal", "icon/filter_icon/icon_filter_fractal.png"));
        lst.add(new EffectItem(16, "Honey Dew", "icon/filter_icon/icon_filter_honeydew.png"));
        lst.add(new EffectItem(17, "Maroon", "icon/filter_icon/icon_filter_maroon.png"));
        lst.add(new EffectItem(18, "See Green", "icon/filter_icon/icon_filter_seegreen.png"));
        lst.add(new EffectItem(19, "Ghost White", "icon/filter_icon/icon_filter_ghostwhite.png"));
        lst.add(new EffectItem(20, "Dark Red", "icon/filter_icon/icon_filter_darkred.png"));

        return lst;
    }
}
