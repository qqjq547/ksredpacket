package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.JxwUserInfoUrl;
import com.guochuang.mimedia.mvp.model.NestLocation;

import java.util.List;

public interface GameView {
    void setData(JxwUserInfoUrl data);
    void setError(String msg);
}
