package com.guochuang.mimedia.mvp.model;

public class RedbagMenu {
    private int nameResId;
    private int iconResId;
    private String type;

    public RedbagMenu(int nameResId, int iconResId, String type) {
        this.nameResId = nameResId;
        this.iconResId = iconResId;
        this.type = type;
    }

    public int getNameResId() {
        return nameResId;
    }

    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
