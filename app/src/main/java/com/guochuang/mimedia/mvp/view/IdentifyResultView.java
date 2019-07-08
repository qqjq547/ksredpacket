package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NameAuthGet;

public interface IdentifyResultView {
    void setData(NameAuthGet data);

    void setError(String msg);

}
