package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.Captcha;

public interface BindingPhoneView {

    void setData(BindingPhone data);
    void setError(String msg);
    void setCaptchaData(Captcha data);
    void setCaptchaError(String msg);
    void setSmsData(String data);
    void setSmsError(String msg);
    void getLogout(String data);
    void getLogoutError(String data);

    void mobileExisted(String data);
}
