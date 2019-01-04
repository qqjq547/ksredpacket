package com.guochuang.mimedia.mvp.view;

import com.guochuang.mimedia.http.response.Page;
import com.guochuang.mimedia.mvp.model.CityBidRecord;

public interface CityBidRecordView {
    void setData(Page<CityBidRecord> data);
    void setError(String msg);
}
