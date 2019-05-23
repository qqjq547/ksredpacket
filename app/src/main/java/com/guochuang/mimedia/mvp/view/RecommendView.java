package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestLocation;
import com.guochuang.mimedia.mvp.model.RecommendData;
import com.guochuang.mimedia.mvp.model.RecommendDetail;

import java.util.List;

public interface RecommendView {
    void setData(RecommendData data);
    void setError(String msg);
}
