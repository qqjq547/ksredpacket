package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.LookVideoResult;

public interface LookView {
    void setData(LookVideoResult data);

    void setError(String message);
}
