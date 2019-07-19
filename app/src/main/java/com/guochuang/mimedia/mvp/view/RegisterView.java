package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.Captcha;

public interface RegisterView {
    void setData(Boolean data);
    void setError(String msg);
    void setVerifyData(Captcha data);
    void setVerifyError(String msg);
    void setSmsData(String data);
    void setSmsError(String msg);
    void setCaptchaType(Integer data);
    void setCaptchaTypeError(String msg);
}
