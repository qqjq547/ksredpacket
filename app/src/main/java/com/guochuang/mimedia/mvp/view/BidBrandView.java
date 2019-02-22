package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.model.NestTimeInfo;

import java.util.List;

public interface BidBrandView {
    void setTimeInfo(NestTimeInfo data);
    void setError(String msg);
}
