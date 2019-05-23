package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.UserLogin;

public interface LoginView {
    void setLoginData(String data);
    void setWxLoginData(String data);
    void setLoginError(String msg);

    void goToAplayWeixin();

}
