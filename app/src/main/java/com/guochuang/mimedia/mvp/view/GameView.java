package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.JxwUserInfoUrl;

public interface GameView {
    void setData(JxwUserInfoUrl data);
    void setError(String msg);
}
