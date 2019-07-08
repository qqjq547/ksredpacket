package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.MyRegion;

public interface MyCityView {
    void setData(MyRegion data);
    void setIsLock(Boolean data);
    void setError(String msg);
}
