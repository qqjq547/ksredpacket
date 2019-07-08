package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.RecommendData;

public interface RecommendView {
    void setData(RecommendData data);
    void setError(String msg);
}
