package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestLocation;

import java.util.List;

public interface AdBidView {
    void setData(List<NestLocation> data);
    void setError(String msg);
}
