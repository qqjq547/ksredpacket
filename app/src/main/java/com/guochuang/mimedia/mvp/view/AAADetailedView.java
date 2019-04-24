package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.AAADetail;

public interface AAADetailedView {
    void setError(String message);

    void setData(Page<AAADetail> data);
}
