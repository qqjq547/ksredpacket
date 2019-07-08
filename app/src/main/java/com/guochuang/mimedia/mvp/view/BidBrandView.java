package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestTimeInfo;

public interface BidBrandView {
    void setTimeInfo(NestTimeInfo data);
    void setError(String msg);
}
