package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.MyWechat;

public interface MyWechatView {
    void setData(MyWechat data);
    void setError(String msg);

    void setBindWxError(String message);

    void setBindWxData(String data);
}
