package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.PayeeUser;


public interface MyCaptureView {
    void setData(PayeeUser data);
    void setError(String msg);
}
