package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.GeoCode;

/**
 * Created by Administrator on 2017-11-28 0028.
 */

public interface MapPickView {
    void setData(GeoCode data);
    void setError(String msg);
}
