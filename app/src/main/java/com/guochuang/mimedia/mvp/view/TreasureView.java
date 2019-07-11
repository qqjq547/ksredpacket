package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Order;
import com.guochuang.mimedia.mvp.model.Snatch;


public interface TreasureView {
    void setData(Page<Snatch> data);
    void setError(String msg);
}
