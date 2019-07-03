package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;

public interface QcTranSealView {
    void setConfig(ExchangeConfig data);
    void setIntCal(DigitalIntCal data);
    void setData(String data);
    void setError(String msg);
}
