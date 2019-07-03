package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.SealRate;
import com.guochuang.mimedia.mvp.model.MySeal;

public interface MySealView {
    void setError(String message);

    void setData(MySeal data);

    void setAAARate(SealRate data);
}
