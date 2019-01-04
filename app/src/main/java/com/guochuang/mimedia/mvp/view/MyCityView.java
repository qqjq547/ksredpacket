package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.MyCity;
import com.guochuang.mimedia.mvp.model.MyRegion;
import com.guochuang.mimedia.mvp.model.RegionDetail;

public interface MyCityView {
    void setData(MyRegion data);
    void setIsLock(Boolean data);
    void setError(String msg);
}
