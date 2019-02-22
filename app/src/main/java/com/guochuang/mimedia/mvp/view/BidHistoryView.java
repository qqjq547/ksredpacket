package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.NestAuctionRecord;
import com.guochuang.mimedia.mvp.model.NestHistory;

public interface BidHistoryView {
    void setData(Page<NestAuctionRecord> data);
    void setError(String msg);
}
