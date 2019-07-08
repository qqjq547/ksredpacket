package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.BindingPhone;

public interface BindingPhoneView {

    void setData(BindingPhone data);
    void setError(String msg);
    void setEmailCaptchaData(String data);
    void setEmailCaptchaError(String msg);
    void setSmsData(String data);
    void setSmsError(String msg);
}
