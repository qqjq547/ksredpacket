package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.mvp.model.NestAuctionRecord;

import java.util.List;

public interface BidHistoryView {
    void setData(List<NestAuctionRecord> data);
    void setError(String msg);
}
