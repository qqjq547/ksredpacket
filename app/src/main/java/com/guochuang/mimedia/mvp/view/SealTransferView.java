package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;

public interface SealTransferView {
    void setConfig(ExchangeConfig data);
    void setIntCal(DigitalIntCal data);
    void setData(Boolean data);
    void setSmsCode(Boolean data);
    void setError(String msg);
}
