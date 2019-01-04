package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.MyKsb;

public interface MyKsbGrantView {
    void setData(String data);

    void setError(String msg);

    void setKsbPreiceData(MyKsb data);

    void setKsbPreiceError(String msg);
}