package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.NestHistory;

public interface BidHistoryView {
    void setData(Page<NestHistory> data);
    void setError(String msg);
}
