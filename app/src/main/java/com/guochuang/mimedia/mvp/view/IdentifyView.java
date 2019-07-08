package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Captcha;

public interface IdentifyView {
    void setData(String data);

    void setError(String msg);

    void setVerifyError(String message);

    void setVerifyData(Captcha data);
}
