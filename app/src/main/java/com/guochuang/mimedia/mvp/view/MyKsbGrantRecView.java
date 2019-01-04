package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.MyKsbGrantRec;

public interface MyKsbGrantRecView {
    void setData(Page<MyKsbGrantRec> data);

    void setError(String msg);
}
