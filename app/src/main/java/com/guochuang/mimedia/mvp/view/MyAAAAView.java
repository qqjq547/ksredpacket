package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.AAARate;
import com.guochuang.mimedia.mvp.model.MyAAA;

public interface MyAAAAView {
    void setError(String message);

    void setData(MyAAA data);

    void setAAARate(AAARate data);
}
