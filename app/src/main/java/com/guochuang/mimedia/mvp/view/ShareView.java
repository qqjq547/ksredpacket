package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.QrCode;

public interface ShareView {
    void setData(QrCode data);
    void setError(String msg);
}
