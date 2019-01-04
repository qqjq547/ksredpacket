package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.AlipayAccout;

public interface MyAlipayView {
    void setData(String data);

    void setError(String msg);

    void setGetData(AlipayAccout data);

    void setGetError(String msg);
}
