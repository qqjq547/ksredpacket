package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.FengWoVideoOrLink;

public interface IntefaceWebView {
    void setData(FengWoVideoOrLink data);

    void setError(String message);
}
