package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.AAARate;
import com.guochuang.mimedia.mvp.model.MyAAA;
import com.guochuang.mimedia.mvp.model.MySeal;

public interface MySealView {
    void setError(String message);

    void setData(MySeal data);

    void setAAARate(AAARate data);
}
