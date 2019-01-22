package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.Address;
import com.guochuang.mimedia.mvp.model.Snatch;


public interface MyTreasureView {
    void setData(Page<Snatch> data);

    void setError(String msg);
}
