package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MyAd;
import com.guochuang.mimedia.mvp.model.NestStatistics;

public interface MyAdView {
    void setStatistics(NestStatistics data);
    void setError(String msg);
}
