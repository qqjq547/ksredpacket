package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.SlideVerifyData;

public interface SlideVerifyView {
    void setData(SlideVerifyData data);
    void setError(String msg);
    void setVerifyData(Boolean data);
    void setVerifyError(String data);
}
