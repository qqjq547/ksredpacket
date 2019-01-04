package com.guochuang.mimedia.mvp.model;

public class MyMenuItem {
    int iconResId;
    int nameResId;

    public MyMenuItem(int iconResId, int nameResId) {
        this.iconResId = iconResId;
        this.nameResId = nameResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getNameResId() {
        return nameResId;
    }

    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }
}
