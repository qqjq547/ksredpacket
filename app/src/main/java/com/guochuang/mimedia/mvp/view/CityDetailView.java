package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.RegionDetail;

public interface CityDetailView {
    void setData(RegionDetail data);
    void setError(String msg);
}
