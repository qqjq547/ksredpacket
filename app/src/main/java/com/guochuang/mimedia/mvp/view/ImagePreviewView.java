package com.guochuang.mimedia.mvp.view;

import android.graphics.Bitmap;

public interface ImagePreviewView {
    void setData(Bitmap data);
    void setError(String msg);
}
