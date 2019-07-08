package com.guochuang.mimedia.mvp.view;

public interface LoginView {
    void setLoginData(String data);
    void setWxLoginData(String data);
    void setLoginError(String msg);

    void goToAplayWeixin();

}
