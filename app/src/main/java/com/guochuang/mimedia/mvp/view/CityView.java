package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.CurrentRegion;

public interface CityView {
    void setData(CurrentRegion data);
    void setError(String msg);
}
