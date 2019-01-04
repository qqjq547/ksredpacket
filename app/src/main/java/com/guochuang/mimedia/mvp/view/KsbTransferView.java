package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.KsbTransfer;
import com.guochuang.mimedia.mvp.model.MyKsb;

public interface KsbTransferView {
    void setData(String data);

    void setError(String msg);

    void setConvertData(KsbTransfer data);

    void setConvertError(String msg);

    void setKsbPreiceData(MyKsb data);

    void setKsbPreiceError(String msg);
}
