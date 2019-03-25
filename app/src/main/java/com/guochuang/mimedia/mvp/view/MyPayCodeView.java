package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.PayCode;


public interface MyPayCodeView {
    void setData(PayCode data);
    void setError(String msg);
}
