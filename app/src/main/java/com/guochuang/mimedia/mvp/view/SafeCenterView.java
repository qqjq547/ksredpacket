package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.SafeCenter;

public interface SafeCenterView {
    void setData(SafeCenter data);

    void setError(String msg);

    void setBindWxData(String data);

    void setBindWxError(String msg);
}
