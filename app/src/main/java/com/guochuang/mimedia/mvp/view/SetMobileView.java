package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.BindingPhone;
import com.guochuang.mimedia.mvp.model.Captcha;

public interface SetMobileView {

    void setData(BindingPhone data);
    void setError(String msg);
    void setCaptchaData(Captcha data);
    void setCaptchaError(String msg);
    void setSmsData(String data);
    void setSmsError(String msg);
    void setCaptchaIsEnabled(Boolean data);

    void mobileExisted(Integer data);
}