package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.DigitalIntCal;
import com.guochuang.mimedia.mvp.model.ExchangeConfig;

public interface AaaTransferView {
    void setConfig(ExchangeConfig data);
    void setIntCal(DigitalIntCal data);
    void setData(Boolean data);
    void setError(String msg);
}
