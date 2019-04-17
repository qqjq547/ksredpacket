package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.RedbagInfo;

public interface LinkRedbagDetailView {
    void setInfo(RedbagInfo data);
    void setError(String msg);
}
