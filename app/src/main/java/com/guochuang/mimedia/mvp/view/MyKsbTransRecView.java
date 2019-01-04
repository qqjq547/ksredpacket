package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MyKsbTransRec;

public interface MyKsbTransRecView {
    void setData(Page<MyKsbTransRec> data);
    void setError(String msg);
}
