package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Captcha;

public interface TradePawView {
    void setData(String data);

    void setError(String msg);

    void setVerifyData(Captcha data);

    void setVerifyError(String msg);

    void setSmsData(String data);

    void setSmsError(String msg);
}
