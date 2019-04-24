package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.ExchangeConfig;
import com.guochuang.mimedia.mvp.model.NestLocation;

import java.util.List;

public interface KsbTranAaaView {
    void setConfig(ExchangeConfig data);
    void setData(String data);
    void setError(String msg);
}
